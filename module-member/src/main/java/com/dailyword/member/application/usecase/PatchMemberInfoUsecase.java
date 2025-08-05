package com.dailyword.member.application.usecase;

import com.dailyword.member.dto.member.PatchMemberInfo;

public interface PatchMemberInfoUsecase {
    void patchMemberInfo(String memberRefCode, PatchMemberInfo patchMemberInfo);
}
