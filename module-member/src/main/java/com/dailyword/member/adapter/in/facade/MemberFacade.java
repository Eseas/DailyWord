package com.dailyword.member.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.member.application.usecase.GetMemberInfoUseCase;
import com.dailyword.member.application.usecase.LoginUseCase;
import com.dailyword.member.application.usecase.PatchMemberInfoUseCase;
import com.dailyword.member.application.usecase.PatchPasswordUseCase;
import com.dailyword.member.dto.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberFacade {

    private final LoginUseCase loginUseCase;
    private final RegisterMemberUseCase registerMemberUseCase;
    private final PatchPasswordUseCase patchPasswordUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final PatchMemberInfoUseCase patchMemberInfoUseCase;

    @GetMapping("/internal/members/{id}")
    public ResponseEntity<APIResponse<GetMemberInfo.Response>> getMemberInfo(
            @PathVariable Long id
    ) {
        GetMemberInfo.Response response = getMemberInfoUseCase.getMemberInfo(id);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(response));
    }

    @PostMapping("/internal/auth/login")
    public ResponseEntity<APIResponse<LoginDto.Response>> login(
            LoginDto.Request requestDto
    ) {
        LoginDto.Response response = loginUseCase.login(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(response));
    }

    @PostMapping("/internal/auth/members/signup")
    public ResponseEntity<APIResponse<RegisterMember.Response>> signup(
            RegisterMember.Request requestDto
    ) {
        RegisterMember.Response responseDto = registerMemberUseCase.register(requestDto.toCommand());

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(responseDto));
    }

    @PatchMapping("/internal/members/password")
    public ResponseEntity patchPassword(
            @RequestBody PatchPassword.Request requestDto
    ) {
        patchPasswordUseCase.patchPassword(requestDto);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/internal/members/{memberId}/info")
    public ResponseEntity patchMemberInfo(
            @PathVariable Long memberId,
            @RequestBody PatchMemberInfo.Request requestDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(patchMemberInfoUseCase.patchMemberInfo(memberId, requestDto)));
    }

}