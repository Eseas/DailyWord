package com.dailyword.gateway.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.client.MemberClient;
import com.dailyword.gateway.dto.member.GetMemberInfo;
import com.dailyword.gateway.dto.member.Login;
import com.dailyword.gateway.dto.member.PatchMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberGatewayController {

    private final MemberClient memberClient;

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
     * @param requestDto - loginId, pwd
     * @return
     */
    @PostMapping("/gateway/auth/members/login")
    public ResponseEntity<APIResponse> postLogin(
            @RequestBody Login.Request requestDto
    ) {
        memberClient.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/gateway/members/{memberId}/info")
    public ResponseEntity<APIResponse<Long>> patchMemberInfo(
            @PathVariable Long memberId,
            @RequestBody PatchMemberInfo.Request requestDto
    ) {
        memberClient.patchMemberInfo(memberId, requestDto);
        return null;
    }
}
