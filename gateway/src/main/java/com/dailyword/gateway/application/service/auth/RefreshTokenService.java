package com.dailyword.gateway.application.service.auth;

import com.dailyword.gateway.adapter.out.client.AuthClient;
import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.application.usecase.auth.RefreshTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * JWT 토큰 갱신 서비스 (Gateway)
 * Gateway 모듈에서 JWT 토큰 갱신 비즈니스 로직을 담당하며, module-auth와의 통신을 처리합니다.
 * Feign Client를 통해 module-auth의 내부 API를 호출하여 Refresh Token을 통한 새로운 토큰 발급을 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUsecase {

    private final AuthClient authClient;

    /**
     * JWT 토큰 갱신
     * 기존 Refresh Token을 사용하여 새로운 Access Token과 Refresh Token을 발급합니다.
     * module-auth의 내부 API를 호출하여 토큰 갱신을 처리하고 새로운 토큰을 반환합니다.
     *
     * @param request Refresh Token 갱신 요청 데이터 (Refresh Token 포함)
     * @return 새로운 JWT Access Token과 Refresh Token 정보
     */
    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return authClient.refreshToken(request).getData();
    }
}
