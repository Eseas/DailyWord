package com.dailyword.kakao.application.usecase;

import com.dailyword.kakao.dto.TokenResponse;

public interface GenerateTokenUsecase {
    TokenResponse generateToken(String subject);
}