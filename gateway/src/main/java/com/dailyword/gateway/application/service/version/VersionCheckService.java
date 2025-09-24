package com.dailyword.gateway.application.service.version;

import com.dailyword.gateway.dto.version.VersionCheckRequest;
import com.dailyword.gateway.dto.version.VersionCheckResponse;
import com.dailyword.gateway.adapter.out.client.VersionClient;
import com.dailyword.gateway.application.usecase.version.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 버전 확인 서비스 (Gateway)
 * Gateway 모듈에서 버전 확인 관련 비즈니스 로직을 담당하며, module-version과의 통신을 처리합니다.
 * Feign Client를 통해 module-version의 내부 API를 호출하여 모바일 애플리케이션의 버전 확인 기능을 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class VersionCheckService implements VersionCheckUsecase {

    private final VersionClient versionClient;

    /**
     * 앱 버전 확인
     * module-version의 내부 API를 통해 클라이언트의 앱 버전을 확인합니다.
     * properties에 설정된 플랫폼별 최소 버전과 클라이언트 버전을 비교하여
     * 업데이트 필요 여부를 판단합니다.
     *
     * @param request 버전 확인 요청 정보 (앱 버전, 플랫폼 정보 포함)
     * @return 버전 확인 결과 (업데이트 필요 여부, 현재 버전, 필수 버전, 메시지)
     */
    @Override
    public VersionCheckResponse checkVersion(VersionCheckRequest request) {
        return versionClient.checkVersion(request).getData();
    }
}