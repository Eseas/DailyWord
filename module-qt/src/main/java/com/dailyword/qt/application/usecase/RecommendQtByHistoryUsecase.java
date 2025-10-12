package com.dailyword.qt.application.usecase;

import com.dailyword.qt.adapter.in.dto.HistoryBasedQtRecommendationRequest;
import com.dailyword.qt.adapter.in.dto.QtRecommendationResponse;

public interface RecommendQtByHistoryUsecase {
    /**
     * 사용자 히스토리 기반 QT 말씀 추천
     * 이전에 읽은 말씀을 기반으로 다음 말씀을 순차적으로 추천합니다.
     *
     * @param request 히스토리 기반 추천 요청 정보
     * @return 추천된 QT 말씀 목록
     */
    QtRecommendationResponse recommendByHistory(HistoryBasedQtRecommendationRequest request);
}