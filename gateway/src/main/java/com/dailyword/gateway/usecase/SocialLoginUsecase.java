package com.dailyword.gateway.usecase;

import com.dailyword.gateway.dto.auth.TokenResponse;

public interface SocialLoginUsecase {
    TokenResponse kakaoLogin(String kakaoCode);
}
