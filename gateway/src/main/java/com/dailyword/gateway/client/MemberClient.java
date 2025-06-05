package com.dailyword.gateway.client;


import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MemberClient {

    private final WebClient memberWebClient;
    private final WebClient kakaoWebClient;

    public MemberClient(WebClient memberWebClient, WebClient kakaoWebClient) {
        this.memberWebClient = memberWebClient;
        this.kakaoWebClient = kakaoWebClient;
    }

    public GetMemberInfo.Response getMemberInfo(Long memberId) {
        try {
            APIResponse<GetMemberInfo.Response> response = memberWebClient.get()
                    .uri("/internal/member/{id}", memberId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<APIResponse<GetMemberInfo.Response>>() {})
                    .block();

            return response.getData();
        } catch (Exception e) {
            throw new RuntimeException("멤버 정보를 불러오는 데 실패했습니다.", e);
        }
    }

    public UserDetails kakaoLogin(String kakaoCode) {
        try {
            // 카카오 소셜 로그인 진행
            APIResponse<KakaoUserInfoResponse> kakaoResponse = kakaoWebClient.post()
                    .uri("/internal/kakao/login")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<APIResponse<KakaoUserInfoResponse>>() {})
                    .block();

            // DB 확인
            APIResponse<Login.Response> loginResponse = memberWebClient.post()
                    .uri("/internal/members/login")
                    .body(kakaoResponse.getData(), KakaoUserInfoResponse.class)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<APIResponse<Login.Response>>() {})
                    .block();

            if(!loginResponse.isSuccess() && loginResponse.getCode() == 2001) {
                // DB에 회원이 존재하지 않을 때
                APIResponse<Login.Response> registResponse = memberWebClient.post()
                        .uri("/internal/members")
                        .body(kakaoResponse.getData(), KakaoUserInfoResponse.class)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<APIResponse<Login.Response>>() {})
                        .block();

                //
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    public void patchPassword(Long memberId, PatchPassword.Request requestDto) {
        try {
            memberWebClient.patch()
                    .uri("/internal/member/{memberId}/password", memberId)
                    .body(requestDto, PatchPassword.Request.class)
                    .retrieve()
                    .toBodilessEntity()
                    .block(); // 동기 처리
        } catch (Exception e) {
        }
    }

    public void patchMemberInfo(Long memberId, PatchMemberInfo.Request requestDto) {
        try {
            memberWebClient.patch()
                    .uri("/internal/members/{memberId}/info", memberId)
                    .body(requestDto, PatchMemberInfo.Request.class)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
        }
    }
}
