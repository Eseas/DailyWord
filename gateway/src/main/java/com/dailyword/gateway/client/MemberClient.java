package com.dailyword.gateway.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.ErrorCode;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.dto.member.*;
import com.dailyword.gateway.exception.MemberApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
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

    public GetMemberInfo.Response login(KakaoUserInfoResponse kakaoUserInfo) {
        APIResponse<GetMemberInfo.Response> response = memberWebClient.post()
                .uri("/internal/members/login")
                .bodyValue(kakaoUserInfo)
                .retrieve()
                .onStatus(status -> status.isError(), r ->
                        r.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> {
                                    log.error("Member API login error: status={}, body={}", r.statusCode(), body);
                                    return Mono.error(new MemberApiException("Member API login failed", r.statusCode().value()));
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<APIResponse<GetMemberInfo.Response>>() {})
                .block();

        if (response == null) {
            throw new MemberApiException("Member login 응답 없음", 500);
        }

        if (!response.isSuccess() && response.getCode() != ErrorCode.REQUIRED_REGIST_MEMBER.getCode()) {
            throw new MemberApiException("Member login 실패: " + response.getCode(), 400);
        }

        return response.getData();
    }

    public GetMemberInfo.Response register(KakaoUserInfoResponse kakaoUserInfo) {
        APIResponse<GetMemberInfo.Response> response = memberWebClient.post()
                .uri("/internal/members")
                .bodyValue(kakaoUserInfo)
                .retrieve()
                .onStatus(status -> status.isError(), r ->
                        r.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> {
                                    return Mono.error(new MemberApiException("Member API register failed", r.statusCode().value()));
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<APIResponse<GetMemberInfo.Response>>() {})
                .block();

        if (response == null || !response.isSuccess()) {
            throw new MemberApiException("Member register 실패", 400);
        }

        return response.getData();
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
