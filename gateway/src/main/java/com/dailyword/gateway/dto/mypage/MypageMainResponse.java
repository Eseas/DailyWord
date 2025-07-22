package com.dailyword.gateway.dto.mypage;

import com.dailyword.gateway.dto.member.GetMemberInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MypageMainResponse {
    private final String memberRefCode;
    private final String memberLoginId;
    private final String memberName;
    private final String memberEmail;
    private final LocalDate memberBirthday;
    private final List<LocalDate> qtProgressDates;

    public static MypageMainResponse create(GetMemberInfo memberInfo, QtProgressResponse qtProgress) {
        return new MypageMainResponse(
                memberInfo.getMemberRefCode(),
                memberInfo.getMemberLoginId(),
                memberInfo.getMemberName(),
                memberInfo.getMemberEmail(),
                memberInfo.getMemberBirthday(),
                qtProgress.getQtProgressDates()
        );
    }
}
