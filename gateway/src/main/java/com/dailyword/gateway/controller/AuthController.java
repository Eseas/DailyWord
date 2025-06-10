package com.dailyword.gateway.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.client.AuthClient;
import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthClient authClient;

    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = authClient.refreshToken(request);
        return ResponseEntity.ok(APIResponse.success(tokenResponse));
    }
}