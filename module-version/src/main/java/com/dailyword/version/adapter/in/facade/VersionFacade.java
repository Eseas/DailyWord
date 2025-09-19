package com.dailyword.version.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.version.adapter.in.facade.dto.VersionCheckRequest;
import com.dailyword.version.adapter.in.facade.dto.VersionCheckResponse;
import com.dailyword.version.application.usecase.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class VersionFacade {

    private final VersionCheckUsecase versionCheckUsecase;

    @PostMapping("/version/check")
    public ResponseEntity<APIResponse<VersionCheckResponse>> checkVersion(
            @RequestBody VersionCheckRequest request
    ) {
        VersionCheckResponse response = versionCheckUsecase.checkVersion(
                request.getAppVersion(),
                request.getPlatform()
        );
        return ResponseEntity.ok(APIResponse.success(SuccessCode.VERSION_CHECK_SUCCESS, response));
    }
}