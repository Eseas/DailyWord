package com.dailyword.kakao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenInfoResponse {
    private String memberRefCode;

    private TokenInfoResponse(String memberRefCode) {
        this.memberRefCode = memberRefCode;
    }

    public static TokenInfoResponse create(String memberRefCode) {
        return new TokenInfoResponse(memberRefCode);
    }
}
