package com.dailyword.member.dto.member;

import com.dailyword.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PatchMemberInfo {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long memberId;
        private String nickname;

        private Response(Member member) {
            this.memberId = member.getId();
            this.nickname = member.getNickname();
        }

        public static Response toDto(Member member) {
            return new Response(member);
        }
    }
}
