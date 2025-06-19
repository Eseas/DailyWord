package com.dailyword.gateway.application.usecase.auth;

import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;

public interface RefreshTokenUsecase {
    TokenResponse refreshToken(RefreshTokenRequest request);
}
