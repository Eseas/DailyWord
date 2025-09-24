package com.dailyword.gateway.application.service.auth;

import com.dailyword.gateway.adapter.out.client.AuthClient;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.application.usecase.auth.GenerateTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * JWT 토큰 생성 서비스 (Gateway)
 * Gateway 모듈에서 JWT 토큰 생성 비즈니스 로직을 담당하며, module-auth와의 통신을 처리합니다.
 * Feign Client를 통해 module-auth의 내부 API를 호출하여 JWT Access Token과 Refresh Token을 발급합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GenerateTokenService implements GenerateTokenUsecase {

    private final AuthClient authClient;

    /**
     * JWT 토큰 생성
     * 사용자 인증 정보를 바탕으로 JWT Access Token과 Refresh Token을 생성합니다.
     * module-auth의 내부 API를 호출하여 토큰을 발급받습니다.
     *
     * @param request 토큰 생성 요청 데이터 (사용자 ID, 권한 정보 등)
     * @return 생성된 JWT Access Token과 Refresh Token 정보
     */
    @Override
    public TokenResponse generateToken(TokenRequest request) {
        return authClient.generateToken(request).getData();
    }
}
