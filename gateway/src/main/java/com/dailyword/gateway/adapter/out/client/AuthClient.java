package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.auth.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/internal/auth")
@FeignClient(name = "authClient", url = "${module.auth.url}")
public interface AuthClient {

    @GetMapping("/")
    APIResponse<TokenInfoResponse> verifyToken(@RequestBody TokenVerifyRequest request);

    @PostMapping("/token")
    APIResponse<TokenResponse> generateToken(@RequestBody TokenRequest request);

    @PostMapping("/refresh")
    APIResponse<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request);
}
