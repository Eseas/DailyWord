package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import com.dailyword.gateway.application.service.member.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class MemberController {

    private final MemberClient memberClient;
    private final SocialLoginService socialLoginUsecase;

    /**
     * 회원 정보 조회
     * @param memberId
     * @return
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<APIResponse<GetMemberInfo>> getMember(
            @PathVariable Long memberId
    ) {
        var memberInfo = memberClient.getMemberInfo(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(memberInfo);
    }

    /**
     * 로그인
     * @return
     */
    @PostMapping("/auth/members/login/{type}")
    public ResponseEntity<APIResponse> postLogin(
            @PathVariable String type,
            @RequestParam String code
    ) {
        TokenResponse token = socialLoginUsecase.kakaoLogin(code);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", token.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    /**
     * 회원 정보 수정
     * @param memberId
     * @param requestDto
     * @return
     */
    @PatchMapping("/members/{memberId}/info")
    public ResponseEntity<APIResponse<Long>> patchMemberInfo(
            @PathVariable Long memberId,
            @RequestBody PatchMemberInfo.Request requestDto
    ) {
        memberClient.patchMemberInfo(memberId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
