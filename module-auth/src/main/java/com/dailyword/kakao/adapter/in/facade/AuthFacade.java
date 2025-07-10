package com.dailyword.kakao.adapter.in.facade;

import com.dailyword.kakao.application.usecase.ValidateTokenUsecase;
import com.dailyword.kakao.dto.*;
import com.dailyword.kakao.application.usecase.GenerateTokenUsecase;
import com.dailyword.kakao.application.usecase.RefreshTokenUsecase;
import com.dailyword.common.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class AuthFacade {

    private final GenerateTokenUsecase generateTokenUsecase;
    private final RefreshTokenUsecase refreshTokenUsecase;
    private final ValidateTokenUsecase validateTokenUsecase;

    @GetMapping("/tokens/validate")
    public ResponseEntity<APIResponse<TokenInfoResponse>> validateToken(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(validateTokenUsecase.validateToken(bearerToken)));
    }

    @PostMapping("/tokens")
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