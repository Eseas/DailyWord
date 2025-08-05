package com.dailyword.gateway.application.usecase.member;

import com.dailyword.gateway.dto.member.GetMemberInfo;

public interface GetMemberInfoUsecase {
    GetMemberInfo getMemberInfo(String memberRefCode);
}
