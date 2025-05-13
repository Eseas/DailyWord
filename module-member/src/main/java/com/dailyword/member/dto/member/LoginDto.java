package com.dailyword.member.dto.member;

import com.dailyword.member.domain.Member;
import com.dailyword.member.domain.MemberRole;

public class LoginDto {

    public static class Request {
        private String loginId;
        private String password;

        public Request() {
        }

        public Request(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }

        public String getLoginId() {
            return loginId;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class Response {
        private Long id;
        private String name;
        private MemberRole role;

        private Response(Member member) {
            id = member.getId();
            name = member.getName();
            role = member.getRole();
        }

        public static Response toDto(Member member) {
            return new Response(member);
        }
    }
}
