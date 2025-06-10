package com.dailyword.gateway.service;

import com.dailyword.gateway.client.AuthClient;
import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.usecase.RefreshTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUsecaseImpl implements RefreshTokenUsecase {

    private final AuthClient authClient;

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return authClient.refreshToken(request);
    }
}
