package com.dailyword.gateway.application.service.auth;

import com.dailyword.gateway.adapter.out.client.AuthClient;
import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.application.usecase.auth.RefreshTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUsecase {

    private final AuthClient authClient;

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return authClient.refreshToken(request).getData();
    }
}
