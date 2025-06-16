package com.dailyword.kakao.application.usecase;

import com.dailyword.kakao.dto.TokenResponse;

public interface RefreshTokenUsecase {
    TokenResponse refreshToken(String refreshToken);
}
