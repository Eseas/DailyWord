package com.dailyword.kakao.application.service;

import com.dailyword.kakao.config.AuthProperties;
import com.dailyword.kakao.dto.TokenResponse;
import com.dailyword.kakao.exception.InvalidRefreshTokenException;
import com.dailyword.kakao.application.usecase.RefreshTokenUsecase;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenUsecaseImpl implements RefreshTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthProperties authProperties;

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
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .accessTokenExpiresIn(newToken.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(newToken.getRefreshTokenExpiresIn())
                .build();
    }
}
