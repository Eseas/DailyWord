package com.dailyword.auth.application.service;

import com.dailyword.auth.dto.TokenResponse;
import com.dailyword.auth.application.usecase.GenerateTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateTokenService implements GenerateTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponse generateToken(String subject) {
        TokenResponse token = jwtTokenProvider.generateToken(subject);

        return TokenResponse.builder()
                .accessToken(makeBearerToken(token.getAccessToken()))
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiresIn(token.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(token.getRefreshTokenExpiresIn())
                .build();
    }

    private String makeBearerToken(String token) {
        return "Bearer " + token;
    }
}
