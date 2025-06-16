package com.dailyword.kakao.application.service;

import com.dailyword.kakao.dto.TokenResponse;
import com.dailyword.kakao.application.usecase.GenerateTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateTokenUsecaseImpl implements GenerateTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponse generateToken(String subject) {
        TokenResponse token = jwtTokenProvider.generateToken(subject);

        return TokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiresIn(token.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(token.getRefreshTokenExpiresIn())
                .build();
    }
}
