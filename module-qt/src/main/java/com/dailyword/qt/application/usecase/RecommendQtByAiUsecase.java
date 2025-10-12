package com.dailyword.qt.application.usecase;

import com.dailyword.qt.adapter.in.dto.AiQtRecommendationRequest;
import com.dailyword.qt.adapter.in.dto.QtRecommendationResponse;

public interface RecommendQtByAiUsecase {
    /**
     * AI를 활용한 맞춤형 QT 말씀 추천
     * 사용자의 답변과 현재 상태를 기반으로 적절한 말씀을 추천합니다.
     *
     * @param request AI 기반 추천 요청 정보
     * @return 추천된 QT 말씀 목록
     */
    QtRecommendationResponse recommendByAi(AiQtRecommendationRequest request);
}