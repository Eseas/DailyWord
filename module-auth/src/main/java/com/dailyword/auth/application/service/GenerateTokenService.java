package com.dailyword.auth.application.service;

import com.dailyword.auth.dto.TokenResponse;
import com.dailyword.auth.application.usecase.GenerateTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 토큰 생성 서비스
 * JWT 토큰 생성을 담당하는 비즈니스 로직을 처리합니다.
 * Access Token과 Refresh Token을 생성하고 Bearer 형식으로 변환하여 반환합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GenerateTokenService implements GenerateTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * JWT 토큰 생성
     * 사용자 식별자를 기반으로 Access Token과 Refresh Token을 생성합니다.
     * Access Token은 Bearer 형식으로 변환하여 반환합니다.
     *
     * @param subject 토큰 생성의 주체가 되는 사용자 식별자 (memberRefCode)
     * @return 생성된 토큰 정보 (Access Token, Refresh Token, 만료시간)
     */
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

    /**
     * Bearer 토큰 형식으로 변환
     * Access Token에 "Bearer " 접두사를 추가하여 HTTP Authorization 헤더에서 사용할 수 있는 형식으로 변환합니다.
     *
     * @param token 원본 JWT 토큰
     * @return Bearer 형식의 토큰 ("Bearer " + token)
     */
    private String makeBearerToken(String token) {
        return "Bearer " + token;
    }
}
