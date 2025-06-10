package com.dailyword.gateway.service;

import com.dailyword.gateway.client.AuthClient;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.usecase.GenerateTokenUsecase;
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
