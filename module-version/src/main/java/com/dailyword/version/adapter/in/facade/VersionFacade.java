package com.dailyword.version.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.version.adapter.in.facade.dto.VersionCheckRequest;
import com.dailyword.version.adapter.in.facade.dto.VersionCheckResponse;
import com.dailyword.version.application.usecase.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 버전 관리 Facade
 * 모바일 애플리케이션의 버전 확인 및 업데이트 필요 여부를 판단하는 내부 API를 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class VersionFacade {

    private final VersionCheckUsecase versionCheckUsecase;

    /**
     * 앱 버전 확인
     * 클라이언트의 앱 버전을 확인하여 업데이트가 필요한지 판단합니다.
     * properties에 설정된 최소 버전과 비교하여 업데이트 필요 여부를 결정합니다.
     *
     * @param request 버전 확인 요청 (앱 버전, 플랫폼 정보 포함)
     * @return 버전 확인 결과 (업데이트 필요 여부, 현재 버전, 필수 버전, 메시지)
     */
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