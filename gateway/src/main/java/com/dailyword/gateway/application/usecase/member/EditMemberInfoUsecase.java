package com.dailyword.gateway.application.usecase.member;

import com.dailyword.gateway.dto.member.PatchMemberInfo;

public interface EditMemberInfoUsecase {
    void editInfo(String memberRefCode, PatchMemberInfo.Request request);
}
