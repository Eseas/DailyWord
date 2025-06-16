package com.dailyword.post.dto.member;

public class PatchPassword {

    public static class Request {
        private Long memberId;
        private String newPassword;

        public Long getMemberId() {
            return memberId;
        }

        public String getNewPassword() {
            return newPassword;
        }
    }
}
