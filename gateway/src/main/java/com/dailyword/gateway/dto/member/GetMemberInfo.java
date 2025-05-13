package com.dailyword.gateway.dto.member;

import java.time.LocalDate;

public class GetMemberInfo {

    public static class Response {
        Long memberId;
        String memberLoginId;
        String memberName;
        String memberEmail;
        LocalDate memberBirthday;

        public Long getMemberId() {
            return memberId;
        }

        public String getMemberLoginId() {
            return memberLoginId;
        }

        public String getMemberName() {
            return memberName;
        }

        public String getMemberEmail() {
            return memberEmail;
        }

        public LocalDate getMemberBirthday() {
            return memberBirthday;
        }
    }
}
