package com.dailyword.gateway.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.client.MemberClient;
import com.dailyword.gateway.dto.member.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberGatewayController {

    private final MemberClient memberClient;

    /**
     * 로그인
     * @param requestDto - loginId, pwd
     * @return
     */
    @PostMapping("/gateway/auth/member/login")
    public ResponseEntity<APIResponse> postLogin(
            @RequestBody Login.Request requestDto
    ) {
        memberClient.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
