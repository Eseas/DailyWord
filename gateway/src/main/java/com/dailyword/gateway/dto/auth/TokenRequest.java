package com.dailyword.gateway.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class TokenRequest {
    private String memberRefCode;
}
