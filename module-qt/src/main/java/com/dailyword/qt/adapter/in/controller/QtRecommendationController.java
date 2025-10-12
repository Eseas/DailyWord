package com.dailyword.qt.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.qt.adapter.in.dto.AiQtRecommendationRequest;
import com.dailyword.qt.adapter.in.dto.HistoryBasedQtRecommendationRequest;
import com.dailyword.qt.adapter.in.dto.QtRecommendationResponse;
import com.dailyword.qt.application.service.QtHistoryService;
import com.dailyword.qt.application.usecase.RecommendQtByAiUsecase;
import com.dailyword.qt.application.usecase.RecommendQtByHistoryUsecase;
import com.dailyword.qt.application.usecase.RecordQtHistoryUsecase;
import com.dailyword.qt.domain.model.entity.UserQtHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * QT 추천 컨트롤러
 * AI 기반 및 히스토리 기반 QT 말씀 추천 API를 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/qt")
@RequiredArgsConstructor
public class QtRecommendationController {

    private final RecommendQtByAiUsecase recommendQtByAiUsecase;
    private final RecommendQtByHistoryUsecase recommendQtByHistoryUsecase;
    private final RecordQtHistoryUsecase recordQtHistoryUsecase;
    private final QtHistoryService qtHistoryService;

    // TODO - 팩토리 메서드 사용하도록 변경 필요.
    /**
     * AI 기반 QT 추천
     * 사용자의 답변과 감정 상태를 분석하여 맞춤형 말씀을 추천합니다.
     *
     * @param request AI 기반 추천 요청
     * @return 추천된 QT 말씀 목록
     */
    @PostMapping("/recommendations/ai")
    public ResponseEntity<APIResponse<QtRecommendationResponse>> recommendByAi(
            @Valid @RequestBody AiQtRecommendationRequest request) {

        log.info("AI-based QT recommendation requested with {} responses",
                request.getUserResponses().size());

        try {
            QtRecommendationResponse response = recommendQtByAiUsecase.recommendByAi(request);
            log.info("AI-based recommendation generated with {} passages",
                    response.getRecommendations().size());

            return ResponseEntity.ok(APIResponse.success(response));
        } catch (Exception e) {
            log.error("Failed to generate AI-based recommendations", e);
            return ResponseEntity.internalServerError()
                .body(APIResponse.error(500, "AI 기반 추천 생성에 실패했습니다."));
        }
    }

    /**
     * 히스토리 기반 QT 추천
     * 사용자의 이전 QT 기록을 바탕으로 다음 읽을 말씀을 추천합니다.
     *
     * @param request 히스토리 기반 추천 요청
     * @return 추천된 QT 말씀 목록
     */
    @PostMapping("/recommendations/history")
    public ResponseEntity<APIResponse<QtRecommendationResponse>> recommendByHistory(
            @Valid @RequestBody HistoryBasedQtRecommendationRequest request) {

        log.info("History-based QT recommendation requested for user: {}", request.getUserId());

        try {
            QtRecommendationResponse response = recommendQtByHistoryUsecase.recommendByHistory(request);
            log.info("History-based recommendation generated with {} passages",
                    response.getRecommendations().size());

            return ResponseEntity.ok(APIResponse.success(response));
        } catch (Exception e) {
            log.error("Failed to generate history-based recommendations for user: {}",
                    request.getUserId(), e);
            return ResponseEntity.internalServerError()
                .body(APIResponse.error(500, "히스토리 기반 추천 생성에 실패했습니다."));
        }
    }

    /**
     * QT 읽기 기록 저장
     * 사용자가 특정 말씀을 읽었음을 기록합니다.
     *
     * @param userId 사용자 ID
     * @param passageId QT 구절 ID
     * @param userNote 사용자 메모 (선택사항)
     * @return 저장된 히스토리 정보
     */
    @PostMapping("/history/record")
    public ResponseEntity<APIResponse<UserQtHistoryResponse>> recordReading(
            @RequestParam Long userId,
            @RequestParam Long passageId,
            @RequestParam(required = false) String userNote) {

        log.info("Recording QT reading for user: {}, passage: {}", userId, passageId);

        try {
            UserQtHistory history = recordQtHistoryUsecase.recordQtReading(userId, passageId, userNote);

            UserQtHistoryResponse response = new UserQtHistoryResponse(
                history.getId(),
                history.getUserId(),
                history.getQtPassage().getId(),
                history.getReadAt(),
                history.getIsCompleted(),
                history.getUserNote()
            );

            return ResponseEntity.ok(APIResponse.success(response));
        } catch (Exception e) {
            log.error("Failed to record QT reading for user: {}, passage: {}",
                    userId, passageId, e);
            return ResponseEntity.internalServerError()
                .body(APIResponse.error(500, "QT 읽기 기록 저장에 실패했습니다."));
        }
    }

    /**
     * QT 완료 표시
     * 특정 QT를 완료로 표시합니다.
     *
     * @param historyId 히스토리 ID
     * @return 성공 응답
     */
    @PutMapping("/history/{historyId}/complete")
    public ResponseEntity<APIResponse<Void>> markAsCompleted(@PathVariable Long historyId) {
        log.info("Marking QT history as completed: {}", historyId);

        try {
            recordQtHistoryUsecase.markAsCompleted(historyId);
            return ResponseEntity.ok(APIResponse.success());
        } catch (Exception e) {
            log.error("Failed to mark QT as completed: {}", historyId, e);
            return ResponseEntity.internalServerError()
                .body(APIResponse.error(500, "QT 완료 표시에 실패했습니다."));
        }
    }

    /**
     * 사용자 노트 업데이트
     * QT 읽기 기록의 사용자 노트를 업데이트합니다.
     *
     * @param historyId 히스토리 ID
     * @param userNote 업데이트할 노트 내용
     * @return 성공 응답
     */
    @PutMapping("/history/{historyId}/note")
    public ResponseEntity<APIResponse<Void>> updateUserNote(
            @PathVariable Long historyId,
            @RequestBody String userNote) {

        log.info("Updating user note for history: {}", historyId);

        try {
            qtHistoryService.updateUserNote(historyId, userNote);
            return ResponseEntity.ok(APIResponse.success());
        } catch (Exception e) {
            log.error("Failed to update user note for history: {}", historyId, e);
            return ResponseEntity.internalServerError()
                .body(APIResponse.error(500, "사용자 노트 업데이트에 실패했습니다."));
        }
    }

    /**
     * 사용자 QT 통계 조회
     * 사용자의 QT 읽기 통계를 조회합니다.
     *
     * @param userId 사용자 ID
     * @return QT 통계 정보
     */
    @GetMapping("/history/statistics/{userId}")
    public ResponseEntity<APIResponse<QtStatisticsResponse>> getUserStatistics(@PathVariable Long userId) {
        log.info("Fetching QT statistics for user: {}", userId);

        try {
            QtHistoryService.QtStatistics stats = qtHistoryService.getUserStatistics(userId);

            QtStatisticsResponse response = new QtStatisticsResponse(
                stats.getTotalRead(),
                stats.getCompleted(),
                stats.getInProgress(),
                stats.getTotalRead() > 0 ?
                    (double) stats.getCompleted() / stats.getTotalRead() * 100 : 0
            );

            return ResponseEntity.ok(APIResponse.success(response));
        } catch (Exception e) {
            log.error("Failed to fetch statistics for user: {}", userId, e);
            return ResponseEntity.internalServerError()
                .body(APIResponse.error(500, "통계 조회에 실패했습니다."));
        }
    }

    /**
     * 사용자 QT 히스토리 응답 DTO
     */
    @lombok.Getter
    @lombok.AllArgsConstructor
    public static class UserQtHistoryResponse {
        private final Long historyId;
        private final Long userId;
        private final Long passageId;
        private final java.time.LocalDateTime readAt;
        private final Boolean isCompleted;
        private final String userNote;
    }

    /**
     * QT 통계 응답 DTO
     */
    @lombok.Getter
    @lombok.AllArgsConstructor
    public static class QtStatisticsResponse {
        private final long totalRead;
        private final long completed;
        private final long inProgress;
        private final double completionRate;
    }
}