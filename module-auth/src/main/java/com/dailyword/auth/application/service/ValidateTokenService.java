package com.dailyword.auth.application.service;

import com.dailyword.auth.application.usecase.ValidateTokenUsecase;
import com.dailyword.auth.dto.TokenInfoResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 토큰 검증 서비스
 * JWT 토큰의 유효성을 검증하고 토큰에 포함된 정보를 추출하는 비즈니스 로직을 처리합니다.
 * Bearer 토큰 형식을 파싱하여 순수 JWT 토큰을 추출하고 서명 및 만료시간을 검증합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ValidateTokenService implements ValidateTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Bearer 토큰 유효성 검증
     * Authorization 헤더에서 받은 Bearer 토큰을 파싱하고 JWT 토큰의 유효성을 검증합니다.
     * 토큰이 유효한 경우 토큰에 포함된 사용자 정보를 반환합니다.
     *
     * @param bearerToken 검증할 Bearer 토큰 ("Bearer " + JWT 토큰)
     * @return 토큰 정보 (사용자 식별자 포함)
     * @throws RuntimeException 토큰이 유효하지 않거나 만료된 경우
     */
    @Override
    public TokenInfoResponse validateToken(String bearerToken) {
        String accessToken = subBearer(bearerToken);

        Claims claims = jwtTokenProvider.validateTokenAndGetClaims(accessToken);

        return TokenInfoResponse.create(claims.getSubject());
    }

    /**
     * Bearer 접두사 제거
     * "Bearer " 접두사가 포함된 토큰에서 순수 JWT 토큰 부분만 추출합니다.
     * Bearer 형식이 아닌 경우 null을 반환합니다.
     *
     * @param bearerToken Bearer 형식의 토큰
     * @return 순수 JWT 토큰 문자열 또는 null
     */
    private String subBearer(String bearerToken) {
        if(bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
