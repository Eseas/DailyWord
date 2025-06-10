package com.dailyword.auth.usecase;

import com.dailyword.auth.dto.TokenResponse;

public interface GenerateTokenUsecase {
    TokenResponse generateToken(String subject);
}