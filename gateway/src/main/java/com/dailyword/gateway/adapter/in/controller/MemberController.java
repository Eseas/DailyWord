package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.adapter.out.client.MemberClient;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import com.dailyword.gateway.application.service.member.SocialLoginUsecaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class MemberController {

    private final MemberClient memberClient;
    private final SocialLoginUsecaseImpl socialLoginUsecase;

    /**
     * 회원 정보 조회
     * @param memberId
     * @return
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<APIResponse<GetMemberInfo.Response>> getMember(
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
        socialLoginUsecase.kakaoLogin(code);
        return ResponseEntity.status(HttpStatus.OK).build();
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
