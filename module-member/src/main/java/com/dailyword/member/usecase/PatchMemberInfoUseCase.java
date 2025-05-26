package com.dailyword.member.usecase;

import com.dailyword.member.dto.member.PatchMemberInfo;

public interface PatchMemberInfoUseCase {

    PatchMemberInfo.Response patchMemberInfo(Long memberId, PatchMemberInfo.Request request);
}
