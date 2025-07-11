package com.dailyword.auth.application.usecase;

import com.dailyword.auth.dto.TokenInfoResponse;

public interface ValidateTokenUsecase {
    TokenInfoResponse validateToken(String accessToken);
}
