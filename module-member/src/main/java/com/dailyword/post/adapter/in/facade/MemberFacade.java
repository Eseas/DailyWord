package com.dailyword.post.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.post.application.usecase.GetMemberInfoUseCase;
import com.dailyword.post.application.usecase.LoginUseCase;
import com.dailyword.post.application.usecase.PatchPasswordUseCase;
import com.dailyword.post.dto.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberFacade {

    private final LoginUseCase loginUseCase;
    private final PatchPasswordUseCase patchPasswordUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;

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
}