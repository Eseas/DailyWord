package com.dailyword.gateway.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.client.MemberClient;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.member.Login;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import com.dailyword.gateway.dto.member.PatchPassword;
import com.dailyword.gateway.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberGatewayController {

    private final MemberClient memberClient;
    private final SocialLoginService socialLoginService;

    /**
     * 회원 정보 조회
     * @param memberId
     * @return
     */
    @GetMapping("/gateway/member/{memberId}")
    public ResponseEntity<APIResponse<GetMemberInfo.Response>> getMember(
            @PathVariable Long memberId
    ) {
        var memberInfo = memberClient.getMemberInfo(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(memberInfo));
    }

    /**
     * 로그인
     * @return
     */
    @PostMapping("/gateway/auth/members/login/{type}")
    public ResponseEntity<APIResponse> postLogin(
            @PathVariable String type,
            @RequestParam String code
    ) {
        socialLoginService.kakaoLoginFlow(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 회원 정보 수정
     * @param memberId
     * @param requestDto
     * @return
     */
    @PatchMapping("/gateway/members/{memberId}/info")
    public ResponseEntity<APIResponse<Long>> patchMemberInfo(
            @PathVariable Long memberId,
            @RequestBody PatchMemberInfo.Request requestDto
    ) {
        memberClient.patchMemberInfo(memberId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/gateway/members/{memberId}/password")
    public ResponseEntity<APIResponse<Long>> patchMemberPassword(
            @PathVariable Long memberId,
            @RequestBody PatchPassword.Request requestDto
    ) {
        memberClient.patchPassword(memberId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
