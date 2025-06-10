package com.dailyword.auth.usecase;

import com.dailyword.auth.dto.TokenResponse;

public interface RefreshTokenUsecase {
    TokenResponse refreshToken(String refreshToken);
}
