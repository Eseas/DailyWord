package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.auth.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/internal/auth")
@FeignClient(name = "authClient", url = "${module.auth.url}")
public interface AuthClient {

    @GetMapping("/tokens/validate")
    APIResponse<TokenInfoResponse> verifyToken(@RequestHeader("Authorization") String accessToken);

    @PostMapping("/tokens")
    APIResponse<TokenResponse> generateToken(@RequestBody TokenRequest request);

    @PostMapping("/refresh")
    APIResponse<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request);
}
