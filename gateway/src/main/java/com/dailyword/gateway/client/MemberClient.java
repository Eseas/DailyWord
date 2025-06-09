package com.dailyword.gateway.client;


import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.ErrorCode;
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

    // TODO -- 커스텀 예외로 변경
    public GetMemberInfo.Response kakaoLogin(String kakaoCode) {
        try {
            // 카카오 소셜 로그인 진행
            APIResponse<KakaoUserInfoResponse> kakaoResponse = kakaoWebClient.post()
                    .uri(uriBuilder -> uriBuilder.path("/internal/kakao/login")
                            .queryParam("code", kakaoCode)
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<APIResponse<KakaoUserInfoResponse>>() {})
                    .block();

            if (kakaoResponse == null || !kakaoResponse.isSuccess()) {
                throw new RuntimeException("카카오 로그인 실패");
            }

            // DB 확인
            APIResponse<GetMemberInfo.Response> loginResponse = memberWebClient.post()
                    .uri("/internal/members/login")
                    .bodyValue(kakaoResponse.getData())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<APIResponse<GetMemberInfo.Response>>() {})
                    .block();

            if (loginResponse == null) {
                throw new RuntimeException("Member login 응답 없음");
            }

            if (!loginResponse.isSuccess() && loginResponse.getCode() == ErrorCode.REQUIRED_REGIST_MEMBER.getCode()) {
                // DB에 회원이 존재하지 않을 때 → 회원가입 진행
                loginResponse = memberWebClient.post()
                        .uri("/internal/members")
                        .bodyValue(kakaoResponse.getData())
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<APIResponse<GetMemberInfo.Response>>() {})
                        .block();

                if (loginResponse == null || !loginResponse.isSuccess()) {
                    throw new RuntimeException("회원가입 실패");
                }
            }

            return loginResponse.getData();

        } catch (Exception e) {
            throw new RuntimeException("카카오 로그인 전체 프로세스 실패");
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
