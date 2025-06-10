package com.dailyword.gateway.usecase;

import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;

public interface GenerateTokenUsecase {
    TokenResponse generateToken(TokenRequest request);
}
