package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.application.usecase.auth.RefreshTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * 인증 관리 Controller
 * 외부 클라이언트의 인증 관련 요청을 처리하는 Gateway API 컨트롤러입니다.
 * JWT 토큰 갱신 기능을 제공하며, module-member와 연동하여 인증 처리를 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway/auth")
public class AuthController {

    private final RefreshTokenUsecase refreshTokenUsecase;

    /**
     * JWT 토큰 갱신
     * Refresh Token을 사용하여 새로운 Access Token과 Refresh Token을 발급합니다.
     * 보안을 위해 새로운 Refresh Token은 HttpOnly 쿠키로 설정되며, Access Token은 Authorization 헤더로 반환됩니다.
     *
     * @param request Refresh Token 갱신 요청 데이터 (refreshToken 포함)
     * @return 새로운 Access Token (Authorization 헤더)과 Refresh Token (HttpOnly 쿠키)
     */
    @PostMapping("/refresh")
    public ResponseEntity refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        TokenResponse token = refreshTokenUsecase.refreshToken(request);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", token.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }
}