package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.application.usecase.member.EditMemberInfoUsecase;
import com.dailyword.gateway.application.usecase.member.GetMemberInfoUsecase;
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

    private final GetMemberInfoUsecase getMemberInfoUsecase;
    private final SocialLoginService socialLoginUsecase;
    private final EditMemberInfoUsecase editMemberInfoUsecase;

    /**
     * 회원 정보 조회
     * @param memberRefCode
     * @return
     */
    @GetMapping("/member/{memberRefCode}")
    public ResponseEntity<APIResponse<GetMemberInfo>> getMember(
            @PathVariable String memberRefCode
    ) {
        var memberInfo = getMemberInfoUsecase.getMemberInfo(memberRefCode);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(memberInfo));
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
                .body(APIResponse.success());
    }

    /**
     * 회원 정보 수정
     * @param memberRefCode
     * @param requestDto
     * @return
     */
    @PatchMapping("/members/{memberRefCode}/info")
    public ResponseEntity<APIResponse<Long>> patchMemberInfo(
            @PathVariable String memberRefCode,
            @RequestBody PatchMemberInfo.Request requestDto
    ) {
        editMemberInfoUsecase.editInfo(memberRefCode, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success());
    }
}
