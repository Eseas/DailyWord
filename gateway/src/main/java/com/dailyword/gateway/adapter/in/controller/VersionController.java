package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.gateway.dto.version.VersionCheckRequest;
import com.dailyword.gateway.dto.version.VersionCheckResponse;
import com.dailyword.gateway.application.usecase.version.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class VersionController {

    private final VersionCheckUsecase versionCheckUsecase;

    /**
     * 앱 버전 체크
     * @param request
     * @return
     */
    @PostMapping("/version/check")
    public ResponseEntity<APIResponse<VersionCheckResponse>> checkVersion(
            @RequestBody VersionCheckRequest request
    ) {
        VersionCheckResponse response = versionCheckUsecase.checkVersion(request);
        return ResponseEntity.ok(APIResponse.success(SuccessCode.VERSION_CHECK_SUCCESS, response));
    }
}