package com.dailyword.member.dto.member;

import com.dailyword.member.domain.model.Member;

import java.time.LocalDate;

public class GetMemberInfo {

    public static class Response {
        String memberRefCode;
        String memberLoginId;
        String memberName;
        String memberEmail;
        LocalDate memberBirthday;

        private Response(Member member) {
            this.memberRefCode = member.getRefCode();
            this.memberLoginId = member.getLoginId();
            this.memberName = member.getName();
            this.memberEmail = member.getEmail();
            this.memberBirthday = member.getBirthday();
        }

        public static Response toDto(Member member) {
            return new Response(member);
        }
    }
}
