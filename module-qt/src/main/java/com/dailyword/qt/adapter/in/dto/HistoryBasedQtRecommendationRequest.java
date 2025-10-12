package com.dailyword.qt.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 히스토리 기반 QT 추천 요청 DTO
 * 사용자의 이전 QT 기록을 바탕으로 다음 말씀을 추천하기 위한 요청 데이터
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBasedQtRecommendationRequest {

    /**
     * 사용자 ID
     */
    private Long userId;

    /**
     * 마지막 QT 날짜 (선택사항)
     * null인 경우 가장 최근 기록을 기준으로 함
     */
    private LocalDate lastQtDate;

    /**
     * 연속 읽기 여부
     * true: 이전 말씀의 바로 다음 구간 추천
     * false: 건너뛰어서 새로운 구간 추천
     */
    private Boolean continuousReading = true;

    /**
     * 추천받고 싶은 말씀 개수 (기본값: 1)
     */
    private Integer recommendationCount = 1;
}