package com.dailyword.gateway.application.service.member;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.gateway.adapter.out.client.AuthClient;
import com.dailyword.gateway.adapter.out.client.KakaoClient;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.exception.MemberApiException;
import com.dailyword.gateway.application.usecase.member.SocialLoginUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLoginUsecaseImpl implements SocialLoginUsecase {

    private final KakaoClient kakaoClient;
    private final MemberClient memberClient;
    private final AuthClient authClient;

    @Override
    public TokenResponse kakaoLogin(String kakaoCode) {

        KakaoUserInfoResponse kakaoUserInfo = kakaoClient.getUserInfo(kakaoCode).getData();

        GetMemberInfo memberInfo = null;

        try {
            memberInfo = memberClient.login(kakaoUserInfo).getData();
        } catch (MemberApiException e) {
            if (e.getStatusCode() == 404 || e.getStatusCode() == ErrorCode.REQUIRED_REGIST_MEMBER.getCode()) {
                memberInfo = memberClient.register(kakaoUserInfo).getData();
            } else {
                throw e;
            }
        }

        return authClient.generateToken(new TokenRequest(memberInfo.getMemberRefCode())).getData();
    }
}
