package com.dailyword.kakao.application.service;

import com.dailyword.kakao.application.usecase.ValidateTokenUsecase;
import com.dailyword.kakao.dto.TokenInfoResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateTokenService implements ValidateTokenUsecase {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenInfoResponse validateToken(String bearerToken) {
        String accessToken = subBearer(bearerToken);

        Claims claims = jwtTokenProvider.validateTokenAndGetClaims(accessToken);

        return TokenInfoResponse.create(claims.getSubject());
    }

    private String subBearer(String bearerToken) {
        if(bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
