package com.dailyword.post.application.usecase;

import com.dailyword.post.dto.member.GetMemberInfo;

public interface GetMemberInfoUseCase {
    GetMemberInfo.Response getMemberInfo(Long memberId);
}
