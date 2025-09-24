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

/**
 * 소셜 로그인 서비스 (Gateway)
 * Gateway 모듈에서 소셜 로그인 비즈니스 로직을 담당하며, 여러 모듈과의 통신을 처리합니다.
 * 카카오 로그인을 통해 사용자 인증을 처리하고, 신규 회원 가입 또는 기존 회원 로그인을 처리합니다.
 * Kakao API, Member API, Auth API를 연동하여 전체 로그인 프로세스를 관리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SocialLoginService implements SocialLoginUsecase {

    private final KakaoClient kakaoClient;
    private final MemberClient memberClient;
    private final AuthClient authClient;

    /**
     * 카카오 소셜 로그인
     * 카카오에서 받은 인증 코드를 통해 로그인을 처리합니다.
     * 1. 카카오 API를 통해 사용자 정보를 가져옴니다.
     * 2. 기존 회원인지 확인하고, 없으면 신규 회원으로 가입시킵니다.
     * 3. JWT 토큰을 생성하여 반환합니다.
     *
     * @param kakaoCode 카카오에서 받은 인증 코드
     * @return 생성된 JWT Access Token과 Refresh Token
     * @throws MemberApiException 회원 처리 중 오류 발생 시
     */
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
