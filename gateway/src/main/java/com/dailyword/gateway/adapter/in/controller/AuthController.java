package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.application.usecase.RefreshTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway/auth")
public class AuthController {

    private final RefreshTokenUsecase refreshTokenUsecase;

    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = refreshTokenUsecase.refreshToken(request);
        return ResponseEntity.ok(APIResponse.success(tokenResponse));
    }
}