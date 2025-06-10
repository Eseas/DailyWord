package com.dailyword.gateway.usecase;

import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;

public interface RefreshTokenUsecase {
    TokenResponse refreshToken(RefreshTokenRequest request);
}
