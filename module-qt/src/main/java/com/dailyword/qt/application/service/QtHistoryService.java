package com.dailyword.qt.application.service;

import com.dailyword.qt.application.usecase.RecordQtHistoryUsecase;
import com.dailyword.qt.domain.model.entity.QtPassage;
import com.dailyword.qt.domain.model.entity.UserQtHistory;
import com.dailyword.qt.infrastructure.db.repository.QtPassageRepository;
import com.dailyword.qt.infrastructure.db.repository.UserQtHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * QT 히스토리 관리 서비스
 * 사용자의 QT 읽기 기록을 관리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QtHistoryService implements RecordQtHistoryUsecase {

    private final UserQtHistoryRepository userQtHistoryRepository;
    private final QtPassageRepository qtPassageRepository;

    @Override
    @Transactional
    public UserQtHistory recordQtReading(Long userId, Long passageId, String userNote) {
        log.debug("Recording QT reading for user: {}, passage: {}", userId, passageId);

        // 이미 읽은 기록이 있는지 확인
        if (userQtHistoryRepository.existsByUserIdAndQtPassageId(userId, passageId)) {
            log.warn("User {} already has reading record for passage {}", userId, passageId);
            // 중복 기록 방지 (필요시 업데이트 로직 추가 가능)
        }

        // QT 구절 조회
        QtPassage passage = qtPassageRepository.findById(passageId)
            .orElseThrow(() -> new IllegalArgumentException("QT 구절을 찾을 수 없습니다: " + passageId));

        // 히스토리 생성 및 저장
        UserQtHistory history = new UserQtHistory(
            userId,
            passage,
            LocalDateTime.now(),
            false,
            userNote
        );

        UserQtHistory saved = userQtHistoryRepository.save(history);

        log.info("QT reading recorded successfully. History ID: {}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void markAsCompleted(Long historyId) {
        log.debug("Marking QT history as completed: {}", historyId);

        UserQtHistory history = userQtHistoryRepository.findById(historyId)
            .orElseThrow(() -> new IllegalArgumentException("QT 기록을 찾을 수 없습니다: " + historyId));

        history.markAsCompleted();
        userQtHistoryRepository.save(history);

        log.info("QT history {} marked as completed", historyId);
    }

    /**
     * 사용자 노트 업데이트
     *
     * @param historyId 히스토리 ID
     * @param userNote 업데이트할 사용자 노트
     */
    @Transactional
    public void updateUserNote(Long historyId, String userNote) {
        log.debug("Updating user note for history: {}", historyId);

        UserQtHistory history = userQtHistoryRepository.findById(historyId)
            .orElseThrow(() -> new IllegalArgumentException("QT 기록을 찾을 수 없습니다: " + historyId));

        history.updateUserNote(userNote);
        userQtHistoryRepository.save(history);

        log.info("User note updated for history: {}", historyId);
    }

    /**
     * 사용자의 QT 통계 조회
     *
     * @param userId 사용자 ID
     * @return QT 통계 정보
     */
    @Transactional(readOnly = true)
    public QtStatistics getUserStatistics(Long userId) {
        long totalRead = userQtHistoryRepository.countByUserIdAndIsCompleted(userId, null);
        long completed = userQtHistoryRepository.countByUserIdAndIsCompleted(userId, true);
        long inProgress = totalRead - completed;

        return new QtStatistics(totalRead, completed, inProgress);
    }

    /**
     * QT 통계 정보 클래스
     */
    public static class QtStatistics {
        private final long totalRead;
        private final long completed;
        private final long inProgress;

        public QtStatistics(long totalRead, long completed, long inProgress) {
            this.totalRead = totalRead;
            this.completed = completed;
            this.inProgress = inProgress;
        }

        public long getTotalRead() { return totalRead; }
        public long getCompleted() { return completed; }
        public long getInProgress() { return inProgress; }
    }
}