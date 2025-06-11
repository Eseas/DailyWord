package com.dailyword.gateway.client;

import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authClient", url = "${module.auth.url}")
public interface AuthClient {

    @PostMapping("/internal/auth/token")
    TokenResponse generateToken(@RequestBody TokenRequest request);

    @PostMapping("/internal/auth/refresh")
    TokenResponse refreshToken(@RequestBody RefreshTokenRequest request);
}
