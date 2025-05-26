package com.dailyword.gateway.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PatchMemberInfo {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private String nickname;
    }
}
