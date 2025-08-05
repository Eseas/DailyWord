package com.dailyword.gateway.application.service.member;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.member.GetMemberInfoUsecase;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMemberInfoService implements GetMemberInfoUsecase {

    private final MemberClient memberClient;

    @Override
    public GetMemberInfo getMemberInfo(String memberRefCode) {
        return memberClient.getMemberInfo(memberRefCode).getData();
    }
}
