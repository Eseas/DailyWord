package com.dailyword.gateway.application.service.mypage;

import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.mypage.GetMypageMainInfoUsecase;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.mypage.MypageMainResponse;
import com.dailyword.gateway.dto.mypage.QtProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMypageMainInfoService implements GetMypageMainInfoUsecase {

    private final MemberClient memberClient;
    private final PostClient postClient;

    @Override
    public MypageMainResponse getMypageMainInfo(String refCode) {
        Long memberId = memberClient.idByRefCode(refCode).getData();

        GetMemberInfo memberInfo = memberClient.getMemberInfo(memberId).getData();

        QtProgressResponse qtProgress = postClient.getQtProgressDates(memberId).getData();

        return MypageMainResponse.create(memberInfo, qtProgress);
    }
}
