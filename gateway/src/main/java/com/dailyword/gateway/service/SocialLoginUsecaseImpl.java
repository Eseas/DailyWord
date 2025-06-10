package com.dailyword.gateway.service;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.gateway.client.AuthClient;
import com.dailyword.gateway.client.KakaoClient;
import com.dailyword.gateway.client.MemberClient;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.exception.MemberApiException;
import com.dailyword.gateway.usecase.SocialLoginUsecase;
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

        KakaoUserInfoResponse kakaoUserInfo = kakaoClient.getUserInfo(kakaoCode);

        GetMemberInfo.Response memberInfo = null;

        try {
            memberInfo = memberClient.login(kakaoUserInfo);
        } catch (MemberApiException e) {
            if (e.getStatusCode() == 404 || e.getStatusCode() == ErrorCode.REQUIRED_REGIST_MEMBER.getCode()) {
                memberInfo = memberClient.register(kakaoUserInfo);
            } else {
                throw e;
            }
        }

        TokenResponse tokenResponse = authClient.generateToken(new TokenRequest(memberInfo.getMemberId()));

        return tokenResponse;
    }
}
