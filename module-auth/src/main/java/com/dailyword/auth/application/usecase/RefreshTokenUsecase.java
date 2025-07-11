package com.dailyword.auth.application.usecase;

import com.dailyword.auth.dto.TokenResponse;

public interface RefreshTokenUsecase {
    TokenResponse refreshToken(String refreshToken);
}
