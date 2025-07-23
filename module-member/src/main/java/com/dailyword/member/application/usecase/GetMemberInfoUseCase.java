package com.dailyword.member.application.usecase;

import com.dailyword.member.dto.member.GetMemberInfo;

public interface GetMemberInfoUseCase {
    GetMemberInfo.Response getMemberInfo(Long memberId);
}
