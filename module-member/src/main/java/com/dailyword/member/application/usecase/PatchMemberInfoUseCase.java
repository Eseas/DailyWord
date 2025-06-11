package com.dailyword.member.application.usecase;

import com.dailyword.member.dto.member.PatchMemberInfo;

public interface PatchMemberInfoUseCase {

    PatchMemberInfo.Response patchMemberInfo(Long memberId, PatchMemberInfo.Request request);
}
