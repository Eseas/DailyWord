package com.dailyword.kakao.application.usecase;

import com.dailyword.kakao.dto.TokenInfoResponse;

public interface ValidateTokenUsecase {
    TokenInfoResponse validateToken(String accessToken);
}
