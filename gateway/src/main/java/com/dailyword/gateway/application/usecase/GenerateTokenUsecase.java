package com.dailyword.gateway.application.usecase;

import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;

public interface GenerateTokenUsecase {
    TokenResponse generateToken(TokenRequest request);
}
