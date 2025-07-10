package com.dailyword.gateway.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class GetMemberInfo {

    String memberRefCode;
    String memberLoginId;
    String memberName;
    String memberEmail;
    LocalDate memberBirthday;
}
