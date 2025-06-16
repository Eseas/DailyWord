package com.dailyword.post.dto.member;

import com.dailyword.post.domain.model.Member;

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

        private Response(Member member) {
            id = member.getId();
            name = member.getName();
        }

        public static Response toDto(Member member) {
            return new Response(member);
        }
    }
}
