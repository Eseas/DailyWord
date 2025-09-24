package com.dailyword.auth.adapter.in.facade;

import com.dailyword.auth.application.usecase.ValidateTokenUsecase;
import com.dailyword.auth.dto.*;
import com.dailyword.auth.application.usecase.GenerateTokenUsecase;
import com.dailyword.auth.application.usecase.RefreshTokenUsecase;
import com.dailyword.common.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관리 Facade
 * JWT 토큰 생성, 검증, 갱신 등의 인증 관련 내부 API를 제공합니다.
 * Gateway 모듈에서 호출하여 사용자 인증 및 토큰 관리 기능을 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class AuthFacade {

    private final GenerateTokenUsecase generateTokenUsecase;
    private final RefreshTokenUsecase refreshTokenUsecase;
    private final ValidateTokenUsecase validateTokenUsecase;

    /**
     * 토큰 유효성 검증
     * Bearer 토큰의 유효성을 검증하고 토큰에 포함된 사용자 정보를 반환합니다.
     * JWT 토큰의 서명, 만료시간 등을 검증하여 토큰의 유효성을 확인합니다.
     *
     * @param bearerToken 검증할 Bearer 토큰 (Authorization 헤더)
     * @return 토큰 정보 (사용자 ID, 만료시간 등)
     */
    @GetMapping("/tokens/validate")
    public ResponseEntity<APIResponse<TokenInfoResponse>> validateToken(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(validateTokenUsecase.validateToken(bearerToken)));
    }

    /**
     * 토큰 생성
     * 사용자의 참조 코드를 기반으로 새로운 JWT 토큰을 생성합니다.
     * Access Token과 Refresh Token을 함께 생성하여 반환합니다.
     *
     * @param request 토큰 생성 요청 (사용자 참조 코드 포함)
     * @return 생성된 토큰 정보 (Access Token, Refresh Token)
     */
    @PostMapping("/tokens")
    public ResponseEntity<APIResponse<TokenResponse>> generateToken(@RequestBody TokenRequest request) {
        TokenResponse tokenResponse = generateTokenUsecase.generateToken(request.getMemberRefCode());

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(tokenResponse));
    }

    /**
     * 토큰 갱신
     * Refresh Token을 사용하여 새로운 Access Token을 발급합니다.
     * Refresh Token의 유효성을 확인한 후 새로운 토큰 쌍을 생성합니다.
     *
     * @param request 토큰 갱신 요청 (Refresh Token 포함)
     * @return 갱신된 토큰 정보 (새로운 Access Token, Refresh Token)
     */
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = refreshTokenUsecase.refreshToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(tokenResponse));
    }
}