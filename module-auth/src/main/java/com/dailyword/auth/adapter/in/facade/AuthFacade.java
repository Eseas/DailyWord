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
        TokenResponse tokenResponse = generateTokenUsecase.generateToken(request.getMemberRefCode());

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(tokenResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = refreshTokenUsecase.refreshToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(tokenResponse));
    }
}