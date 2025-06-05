package com.dailyword.gateway.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {

    public record KakaoAccount(
            String email,
            @JsonProperty("profile") Profile profile,
            @JsonProperty("birthday") String birthday,
            @JsonProperty("birthyear") String birthyear,
            @JsonProperty("name") String name
    ) {
        public record Profile(
                @JsonProperty("nickname") String nickname
        ) {}
    }
}
