package com.dailyword.qt.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * QT 추천 응답 DTO
 * 추천된 말씀 구간 정보를 담는 응답 데이터
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class QtRecommendationResponse {

    /**
     * 추천된 말씀 구간 목록
     */
    private final List<RecommendedPassage> recommendations;

    /**
     * 추천 이유 또는 설명
     */
    private final String recommendationReason;

    /**
     * 추천 유형 (AI_BASED, HISTORY_BASED)
     */
    private final RecommendationType type;

    /**
     * 추천된 말씀 구간 정보
     */
    @Getter
    @AllArgsConstructor
    public static class RecommendedPassage {
        private final Long passageId;
        private final String book;
        private final Integer chapter;
        private final Integer startVerse;
        private final Integer endVerse;
        private final String title;
        private final String summary;
        private final List<String> tags;
        private final String bibleText; // 실제 성경 본문
        private final Double relevanceScore; // AI 추천 시 관련성 점수
    }

    /**
     * 추천 유형
     */
    public enum RecommendationType {
        AI_BASED("AI 기반 추천"),
        HISTORY_BASED("히스토리 기반 추천");

        private final String description;

        RecommendationType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}