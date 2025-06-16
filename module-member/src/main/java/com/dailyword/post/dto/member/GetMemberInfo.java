package com.dailyword.post.dto.member;

import com.dailyword.post.domain.model.Member;

import java.time.LocalDate;

public class GetMemberInfo {

    public static class Response {
        Long memberId;
        String memberLoginId;
        String memberName;
        String memberEmail;
        LocalDate memberBirthday;

        private Response(Member member) {
            this.memberId = member.getId();
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
