package com.dailyword.qt.application.service;

import com.dailyword.qt.adapter.in.dto.HistoryBasedQtRecommendationRequest;
import com.dailyword.qt.adapter.in.dto.QtRecommendationResponse;
import com.dailyword.qt.application.usecase.RecommendQtByHistoryUsecase;
import com.dailyword.qt.domain.model.entity.QtPassage;
import com.dailyword.qt.domain.model.entity.UserQtHistory;
import com.dailyword.qt.infrastructure.db.repository.QtPassageRepository;
import com.dailyword.qt.infrastructure.db.repository.UserQtHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 히스토리 기반 QT 추천 서비스
 * 사용자의 이전 QT 기록을 바탕으로 다음 읽을 말씀을 추천합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryBasedQtRecommendationService implements RecommendQtByHistoryUsecase {

    private final UserQtHistoryRepository userQtHistoryRepository;
    private final QtPassageRepository qtPassageRepository;

    // 성경 책 순서 (구약 -> 신약)
    private static final List<String> BIBLE_BOOK_ORDER = Arrays.asList(
        // 구약
        "창세기", "출애굽기", "레위기", "민수기", "신명기",
        "여호수아", "사사기", "룻기", "사무엘상", "사무엘하",
        "열왕기상", "열왕기하", "역대상", "역대하", "에스라",
        "느헤미야", "에스더", "욥기", "시편", "잠언",
        "전도서", "아가", "이사야", "예레미야", "예레미야애가",
        "에스겔", "다니엘", "호세아", "요엘", "아모스",
        "오바댜", "요나", "미가", "나훔", "하박국",
        "스바냐", "학개", "스가랴", "말라기",
        // 신약
        "마태복음", "마가복음", "누가복음", "요한복음", "사도행전",
        "로마서", "고린도전서", "고린도후서", "갈라디아서", "에베소서",
        "빌립보서", "골로새서", "데살로니가전서", "데살로니가후서",
        "디모데전서", "디모데후서", "디도서", "빌레몬서", "히브리서",
        "야고보서", "베드로전서", "베드로후서", "요한일서", "요한이서",
        "요한삼서", "유다서", "요한계시록"
    );

    @Override
    @Transactional(readOnly = true)
    public QtRecommendationResponse recommendByHistory(HistoryBasedQtRecommendationRequest request) {
        log.info("Starting history-based QT recommendation for user: {}", request.getUserId());

        try {
            // 1. 사용자의 마지막 QT 기록 조회
            Optional<UserQtHistory> lastHistory = userQtHistoryRepository
                .findTopByUserIdOrderByReadAtDesc(request.getUserId());

            List<QtRecommendationResponse.RecommendedPassage> recommendations;

            if (lastHistory.isPresent()) {
                // 히스토리가 있는 경우
                if (request.getContinuousReading()) {
                    // 연속 읽기 모드
                    recommendations = recommendContinuous(lastHistory.get(), request);
                } else {
                    // 건너뛰기 모드 (새로운 구간 추천)
                    recommendations = recommendNewSection(request.getUserId(), request);
                }
            } else {
                // 히스토리가 없는 경우 (첫 QT)
                recommendations = recommendForFirstTime(request);
            }

            String reason = generateHistoryBasedReason(lastHistory.orElse(null), request);

            return new QtRecommendationResponse(
                recommendations,
                reason,
                QtRecommendationResponse.RecommendationType.HISTORY_BASED
            );

        } catch (Exception e) {
            log.error("Failed to generate history-based recommendations for user: {}",
                     request.getUserId(), e);
            throw new RuntimeException("히스토리 기반 추천 생성 실패", e);
        }
    }

    /**
     * 연속 읽기 추천
     * 마지막으로 읽은 구절의 다음 구절을 추천
     */
    private List<QtRecommendationResponse.RecommendedPassage> recommendContinuous(
            UserQtHistory lastHistory, HistoryBasedQtRecommendationRequest request) {

        QtPassage lastPassage = lastHistory.getQtPassage();
        List<QtRecommendationResponse.RecommendedPassage> recommendations = new ArrayList<>();

        // 같은 장의 다음 구절 찾기
        List<QtPassage> nextInChapter = qtPassageRepository.findNextPassagesInChapter(
            lastPassage.getBook(),
            lastPassage.getChapter(),
            lastPassage.getEndVerse()
        );

        if (!nextInChapter.isEmpty()) {
            // 같은 장에 다음 구절이 있는 경우
            nextInChapter.stream()
                .limit(request.getRecommendationCount())
                .forEach(p -> recommendations.add(buildRecommendedPassage(p)));
        } else {
            // 다음 장으로 이동
            recommendations.addAll(recommendNextChapter(lastPassage, request));
        }

        return recommendations;
    }

    /**
     * 다음 장 추천
     */
    private List<QtRecommendationResponse.RecommendedPassage> recommendNextChapter(
            QtPassage lastPassage, HistoryBasedQtRecommendationRequest request) {

        List<QtRecommendationResponse.RecommendedPassage> recommendations = new ArrayList<>();

        // 다음 장의 구절들 조회
        List<QtPassage> nextChapterPassages = qtPassageRepository
            .findByBookAndChapterOrderByStartVerse(
                lastPassage.getBook(),
                lastPassage.getChapter() + 1
            );

        if (!nextChapterPassages.isEmpty()) {
            // 같은 책의 다음 장이 있는 경우
            nextChapterPassages.stream()
                .limit(request.getRecommendationCount())
                .forEach(p -> recommendations.add(buildRecommendedPassage(p)));
        } else {
            // 다음 책으로 이동
            recommendations.addAll(recommendNextBook(lastPassage.getBook(), request));
        }

        return recommendations;
    }

    /**
     * 다음 책 추천
     */
    private List<QtRecommendationResponse.RecommendedPassage> recommendNextBook(
            String currentBook, HistoryBasedQtRecommendationRequest request) {

        List<QtRecommendationResponse.RecommendedPassage> recommendations = new ArrayList<>();

        int currentIndex = BIBLE_BOOK_ORDER.indexOf(currentBook);
        if (currentIndex >= 0 && currentIndex < BIBLE_BOOK_ORDER.size() - 1) {
            String nextBook = BIBLE_BOOK_ORDER.get(currentIndex + 1);

            // 다음 책의 첫 장 구절들 조회
            List<QtPassage> firstChapterPassages = qtPassageRepository
                .findByBookAndChapterOrderByStartVerse(nextBook, 1);

            firstChapterPassages.stream()
                .limit(request.getRecommendationCount())
                .forEach(p -> recommendations.add(buildRecommendedPassage(p)));
        } else {
            // 마지막 책인 경우 또는 책을 찾을 수 없는 경우, 처음으로 돌아가기
            return recommendForFirstTime(request);
        }

        return recommendations;
    }

    /**
     * 새로운 구간 추천
     * 사용자가 읽지 않은 구간 중에서 추천
     */
    private List<QtRecommendationResponse.RecommendedPassage> recommendNewSection(
            Long userId, HistoryBasedQtRecommendationRequest request) {

        // 사용자가 읽은 구절 ID 목록 조회
        List<Long> readPassageIds = userQtHistoryRepository.findReadPassageIdsByUserId(userId);

        // 읽지 않은 구절 중에서 랜덤 선택
        List<QtPassage> allPassages = qtPassageRepository.findAll();
        List<QtPassage> unreadPassages = allPassages.stream()
            .filter(p -> !readPassageIds.contains(p.getId()))
            .collect(Collectors.toList());

        if (unreadPassages.isEmpty()) {
            // 모든 구절을 읽은 경우, 처음부터 다시
            return recommendForFirstTime(request);
        }

        // 랜덤하게 섞고 필요한 개수만큼 선택
        Collections.shuffle(unreadPassages);

        return unreadPassages.stream()
            .limit(request.getRecommendationCount())
            .map(this::buildRecommendedPassage)
            .collect(Collectors.toList());
    }

    /**
     * 첫 QT 추천
     * 사용자가 QT를 처음 시작하는 경우
     */
    private List<QtRecommendationResponse.RecommendedPassage> recommendForFirstTime(
            HistoryBasedQtRecommendationRequest request) {

        List<QtRecommendationResponse.RecommendedPassage> recommendations = new ArrayList<>();

        // 시작 추천 책들 (복음서 중심)
        List<String> starterBooks = Arrays.asList("마태복음", "마가복음", "누가복음", "요한복음", "시편");

        for (String book : starterBooks) {
            List<QtPassage> passages = qtPassageRepository
                .findByBookAndChapterOrderByStartVerse(book, 1);

            if (!passages.isEmpty()) {
                recommendations.add(buildRecommendedPassage(passages.get(0)));
                if (recommendations.size() >= request.getRecommendationCount()) {
                    break;
                }
            }
        }

        // 부족한 경우 랜덤 구절 추가
        if (recommendations.size() < request.getRecommendationCount()) {
            List<QtPassage> randomPassages = qtPassageRepository
                .findRandomPassages(request.getRecommendationCount() - recommendations.size());

            randomPassages.forEach(p -> recommendations.add(buildRecommendedPassage(p)));
        }

        return recommendations;
    }

    /**
     * 추천 구절 객체 생성
     */
    private QtRecommendationResponse.RecommendedPassage buildRecommendedPassage(QtPassage passage) {
        return new QtRecommendationResponse.RecommendedPassage(
            passage.getId(),
            passage.getBook(),
            passage.getChapter(),
            passage.getStartVerse(),
            passage.getEndVerse(),
            passage.getTitle(),
            passage.getSummary(),
            passage.getTags(),
            generateBibleText(passage),
            null // 히스토리 기반에서는 관련성 점수 없음
        );
    }

    /**
     * 성경 본문 생성 (임시)
     */
    private String generateBibleText(QtPassage passage) {
        return String.format("%s %d:%d-%d",
            passage.getBook(), passage.getChapter(),
            passage.getStartVerse(), passage.getEndVerse());
    }

    /**
     * 추천 이유 생성
     */
    private String generateHistoryBasedReason(UserQtHistory lastHistory,
                                              HistoryBasedQtRecommendationRequest request) {
        StringBuilder reason = new StringBuilder();

        if (lastHistory != null) {
            QtPassage lastPassage = lastHistory.getQtPassage();

            if (request.getContinuousReading()) {
                reason.append("마지막으로 읽으신 ")
                      .append(lastPassage.getBook()).append(" ")
                      .append(lastPassage.getChapter()).append("장의 ")
                      .append("다음 구간을 추천드립니다. ");
                reason.append("순서대로 읽으시면서 말씀의 흐름을 이해하실 수 있습니다.");
            } else {
                reason.append("새로운 말씀 구간을 추천드립니다. ");
                reason.append("이전과 다른 관점에서 하나님의 말씀을 묵상하실 수 있습니다.");
            }
        } else {
            reason.append("첫 QT를 시작하시는군요! ");
            reason.append("복음서를 중심으로 읽기 쉬운 말씀을 추천드립니다. ");
            reason.append("매일 꾸준히 말씀을 읽으시면서 은혜를 경험하시길 바랍니다.");
        }

        return reason.toString();
    }
}