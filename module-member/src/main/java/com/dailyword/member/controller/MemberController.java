package com.dailyword.member.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.member.dto.member.LoginDto;
import com.dailyword.member.dto.member.RegisterMember;
import com.dailyword.member.usecase.LoginUseCase;
import com.dailyword.member.usecase.RegisterMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final LoginUseCase loginUseCase;
    private final RegisterMemberUseCase registerMemberUseCase;

    @PostMapping("/internal/auth/login")
    public ResponseEntity<APIResponse<LoginDto.Response>> login(
            LoginDto.Request requestDto
    ) {
        LoginDto.Response response = loginUseCase.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(response));
    }

    @PostMapping("/internal/auth/member/signup")
    public ResponseEntity<APIResponse<RegisterMember.Response>> signup(
            RegisterMember.Request requestDto
    ) {
        RegisterMember.Response responseDto = registerMemberUseCase.register(requestDto.toCommand());
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(responseDto));
    }

}