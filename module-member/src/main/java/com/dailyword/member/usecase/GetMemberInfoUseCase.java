package com.dailyword.member.usecase;

import com.dailyword.member.dto.member.GetMemberInfo;

public interface GetMemberInfoUseCase {
    GetMemberInfo.Response getMemberInfo(Long memberId);
}
