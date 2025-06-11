package com.dailyword.auth.adapter.in.facade;

import com.dailyword.auth.dto.RefreshTokenRequest;
import com.dailyword.auth.dto.TokenRequest;
import com.dailyword.auth.dto.TokenResponse;
import com.dailyword.auth.application.usecase.GenerateTokenUsecase;
import com.dailyword.auth.application.usecase.RefreshTokenUsecase;
import com.dailyword.common.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class AuthFacade {

    private final GenerateTokenUsecase generateTokenUsecase;
    private final RefreshTokenUsecase refreshTokenUsecase;

    @PostMapping("/token")
    public ResponseEntity<APIResponse<TokenResponse>> generateToken(@RequestBody TokenRequest request) {
        TokenResponse tokenResponse = generateTokenUsecase.generateToken(request.getSubject());
        return ResponseEntity.ok(APIResponse.success(tokenResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = refreshTokenUsecase.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
}