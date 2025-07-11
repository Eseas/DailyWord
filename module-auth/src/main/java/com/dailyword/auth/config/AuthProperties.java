package com.dailyword.auth.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private String secretKey;
    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;
}
