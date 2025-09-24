package com.dailyword.auth.application.service;

import com.dailyword.auth.config.AuthProperties;
import com.dailyword.auth.dto.TokenResponse;
import com.dailyword.auth.exception.InvalidRefreshTokenException;
import com.dailyword.auth.application.usecase.RefreshTokenUsecase;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 토큰 갱신 서비스
 * Refresh Token을 사용하여 새로운 Access Token을 발급하는 비즈니스 로직을 처리합니다.
 * Token Rotation 정책을 적용하여 보안을 강화하고, Redis를 통해 Refresh Token의 유효성을 관리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthProperties authProperties;

    /**
     * Refresh Token을 사용한 토큰 갱신
     * 기존 Refresh Token의 유효성을 확인하고 새로운 토큰 쌍을 발급합니다.
     * Token Rotation 정책에 따라 기존 Refresh Token을 무효화하고 새로운 Refresh Token을 생성합니다.
     *
     * @param refreshToken 갱신에 사용할 Refresh Token
     * @return 새로 발급된 토큰 정보 (Access Token, Refresh Token, 만료시간)
     * @throws InvalidRefreshTokenException Refresh Token이 유효하지 않거나 만료된 경우
     */
    @Override
    public TokenResponse refreshToken(String refreshToken) {
        // 블랙리스트 확인 (존재하지 않으면 에러)
        Boolean exists = redisTemplate.hasKey("refresh:" + refreshToken);
        if (Boolean.FALSE.equals(exists)) {
            throw new InvalidRefreshTokenException("Invalid or expired refresh token");
        }

        // RefreshToken 검증
        Claims claims = jwtTokenProvider.validateTokenAndGetClaims(refreshToken);
        String subject = claims.getSubject();

        // 새 토큰 발급
        TokenResponse newToken = jwtTokenProvider.generateToken(subject);

        // 기존 RefreshToken 블랙리스트 등록 (Rotation 적용)
        redisTemplate.delete("refresh:" + refreshToken);

        // 새 RefreshToken 저장
        redisTemplate.opsForValue().set(
                "refresh:" + newToken.getRefreshToken(),
                subject,
                authProperties.getRefreshTokenExpirationMs(),
                TimeUnit.MILLISECONDS
        );

        return TokenResponse.builder()
                .accessToken(makeBearerToken(newToken.getAccessToken()))
                .refreshToken(newToken.getRefreshToken())
                .accessTokenExpiresIn(newToken.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(newToken.getRefreshTokenExpiresIn())
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
