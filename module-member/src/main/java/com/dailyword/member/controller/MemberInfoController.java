package com.dailyword.member.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.member.dto.member.GetMemberInfo;
import com.dailyword.member.dto.member.PatchPassword;
import com.dailyword.member.usecase.GetMemberInfoUseCase;
import com.dailyword.member.usecase.PatchPasswordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberInfoController {

    private final PatchPasswordUseCase patchPasswordUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;

    @GetMapping("/internal/member/{id}")
    public ResponseEntity<APIResponse<GetMemberInfo.Response>> getMemberInfo(
            @PathVariable Long id
    ) {
        GetMemberInfo.Response response = getMemberInfoUseCase.getMemberInfo(id);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(response));
    }

    @PatchMapping("/internal/member/password")
    public ResponseEntity patchPassword(
            @RequestBody PatchPassword.Request requestDto
    ) {
        patchPasswordUseCase.patchPassword(requestDto);

        return ResponseEntity.ok().build();
    }
}
