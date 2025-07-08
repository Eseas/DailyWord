package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.auth.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authClient", url = "${module.auth.url}")
public interface AuthClient {

    APIResponse<TokenInfoResponse> verifyToken(@RequestBody TokenVerifyRequest request);

    @PostMapping("/internal/auth/token")
    TokenResponse generateToken(@RequestBody TokenRequest request);

    @PostMapping("/internal/auth/refresh")
    TokenResponse refreshToken(@RequestBody RefreshTokenRequest request);
}
