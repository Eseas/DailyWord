package com.dailyword.gateway.service;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.gateway.client.KakaoClient;
import com.dailyword.gateway.client.MemberClient;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final KakaoClient kakaoClient;
    private final MemberClient memberClient;

    public GetMemberInfo.Response kakaoLoginFlow(String kakaoCode) {
        // 카카오 유저 정보 조회
        KakaoUserInfoResponse kakaoUserInfo = kakaoClient.getUserInfo(kakaoCode);

        // Member Login 시도
        GetMemberInfo.Response memberInfo = null;

        try {
            memberInfo = memberClient.login(kakaoUserInfo);
        } catch (RuntimeException e) {
            // USER_NOT_FOUND_CODE 케이스인지 확인 후 회원가입 처리
            if (e.getMessage().contains(String.valueOf(ErrorCode.REQUIRED_REGIST_MEMBER))) {
                memberInfo = memberClient.register(kakaoUserInfo);
            } else {
                throw e;  // 다른 에러는 그대로 던짐
            }
        }

        return memberInfo;
    }
}