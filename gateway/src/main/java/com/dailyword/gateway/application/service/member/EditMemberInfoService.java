package com.dailyword.gateway.application.service.member;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.member.EditMemberInfoUsecase;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditMemberInfoService implements EditMemberInfoUsecase {

    private final MemberClient memberClient;

    @Override
    public void editInfo(String memberRefCode, PatchMemberInfo.Request request) {
        memberClient.patchMemberInfo(memberRefCode, request);
    }
}
