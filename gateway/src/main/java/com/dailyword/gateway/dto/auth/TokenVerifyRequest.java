package com.dailyword.gateway.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TokenVerifyRequest {
    String accessToken;

    private TokenVerifyRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public static TokenVerifyRequest create(String accessToken) {
        return new TokenVerifyRequest(accessToken);
    }
}
