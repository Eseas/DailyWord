package com.dailyword.gateway.application.service.mypage;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.MypageClient;
import com.dailyword.gateway.application.usecase.mypage.GetMypageMainInfoUsecase;
import com.dailyword.gateway.dto.mypage.MypageMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMypageMainInfoService implements GetMypageMainInfoUsecase {

    private final MemberClient memberClient;
    private final MypageClient mypageClient;

    @Override
    public MypageMainResponse getMypageMainInfo(String refCode) {

        Long memberId = memberClient.idByRefCode(refCode).getData();

        return mypageClient.getMypageMainInfo(memberId).getData();
    }
}
