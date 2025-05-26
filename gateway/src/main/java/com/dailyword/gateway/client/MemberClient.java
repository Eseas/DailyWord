package com.dailyword.gateway.client;


import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.member.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MemberClient {

    private final WebClient memberWebClient;

    public MemberClient(WebClient memberWebClient) {
        this.memberWebClient = memberWebClient;
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

    public UserDetails login(Login.Request requestDto) {
        try {
            APIResponse<Login.Response> response = memberWebClient.post()
                    .uri("/internal/members/login")
                    .body(requestDto, Login.Request.class)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<APIResponse<Login.Response>>() {})
                    .block();

            return response.getData();
        } catch (Exception e) {
            return null;
        }
    }

    public void signUp(RegisterMember.Request requestDto) {
        try {
            memberWebClient.post()
                    .uri("/internal/member/auth/signup")
                    .body(requestDto, RegisterMember.Request.class)
                    .retrieve()
                    .bodyToMono(RegisterMember.Response.class)
                    .block(); // 동기 처리
        } catch (Exception e) {
        }
    }

    public void patchPassword(PatchPassword.Request requestDto) {
        try {
            memberWebClient.patch()
                    .uri("/internal/member/password")
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
