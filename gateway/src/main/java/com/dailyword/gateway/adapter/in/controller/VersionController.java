package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.gateway.dto.version.VersionCheckRequest;
import com.dailyword.gateway.dto.version.VersionCheckResponse;
import com.dailyword.gateway.application.usecase.version.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 버전 확인 Controller
 * 외부 클라이언트의 앱 버전 확인 요청을 처리하는 Gateway API 컨트롤러입니다.
 * module-version과 연동하여 모바일 애플리케이션의 버전 확인 및 업데이트 필요 여부를 판단합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gateway")
public class VersionController {

    private final VersionCheckUsecase versionCheckUsecase;

    /**
     * 앱 버전 확인
     * 클라이언트의 앱 버전을 확인하여 업데이트가 필요한지 판단합니다.
     * properties에 설정된 플랫폼별 최소 버전과 클라이언트 버전을 비교하여
     * 업데이트 필요 여부, 현재 버전, 필수 버전 정보를 반환합니다.
     *
     * @param request 버전 확인 요청 정보 (앱 버전, 플랫폼 정보 포함)
     * @return 버전 확인 결과 (업데이트 필요 여부, 현재 버전, 필수 버전, 메시지)
     */
    @PostMapping("/version/check")
    public ResponseEntity<APIResponse<VersionCheckResponse>> checkVersion(
            @RequestBody VersionCheckRequest request
    ) {
        VersionCheckResponse response = versionCheckUsecase.checkVersion(request);
        return ResponseEntity.ok(APIResponse.success(SuccessCode.VERSION_CHECK_SUCCESS, response));
    }
}