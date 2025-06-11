package com.dailyword.gateway.application.service;

import com.dailyword.gateway.adapter.out.client.AuthClient;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.application.usecase.GenerateTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateTokenUsecaseImpl implements GenerateTokenUsecase {

    private final AuthClient authClient;

    @Override
    public TokenResponse generateToken(TokenRequest request) {
        return authClient.generateToken(request);
    }
}
