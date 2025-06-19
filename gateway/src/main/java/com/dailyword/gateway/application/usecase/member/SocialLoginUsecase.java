package com.dailyword.gateway.application.usecase.member;

import com.dailyword.gateway.dto.auth.TokenResponse;

public interface SocialLoginUsecase {
    TokenResponse kakaoLogin(String kakaoCode);
}
