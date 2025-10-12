package com.dailyword.qt.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * AI 기반 QT 추천 요청 DTO
 * 사용자의 답변을 기반으로 AI가 적절한 말씀을 추천하기 위한 요청 데이터
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiQtRecommendationRequest {

    /**
     * 사용자가 답변한 문답 배열
     * 예: ["오늘 힘든 일이 있었어요", "위로가 필요해요", "희망을 얻고 싶어요"]
     */
    private List<String> userResponses;

    /**
     * 사용자의 현재 감정 상태 (선택사항)
     * 예: "sad", "anxious", "grateful", "confused"
     */
    private String emotionalState;

    /**
     * 선호하는 성경 범위 (선택사항)
     * 예: "신약", "구약", "시편", "잠언"
     */
    private String preferredBibleSection;

    /**
     * 추천받고 싶은 말씀 개수 (기본값: 3)
     */
    private Integer recommendationCount = 3;
}