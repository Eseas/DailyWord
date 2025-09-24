package com.dailyword.version.application.service;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.version.adapter.in.facade.dto.VersionCheckResponse;
import com.dailyword.version.application.usecase.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 버전 확인 서비스
 * 모바일 애플리케이션의 버전을 확인하고 업데이트 필요 여부를 판단하는 비즈니스 로직을 담당합니다.
 * properties에 설정된 플랫폼별 최소 버전과 클라이언트 버전을 비교합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class VersionCheckService implements VersionCheckUsecase {

    @Value("${app.version.google.playstore}")
    private String googlePlayStoreVersion;

    @Value("${app.version.apple.appstore}")
    private String appleAppStoreVersion;

    /**
     * 앱 버전 확인
     * 클라이언트의 앱 버전을 확인하여 업데이트가 필요한지 판단합니다.
     *
     * @param appVersion 클라이언트의 앱 버전
     * @param platform 플랫폼 (android, ios)
     * @return 버전 확인 결과 (업데이트 필요 여부, 버전 정보, 메시지)
     */
    @Override
    public VersionCheckResponse checkVersion(String appVersion, String platform) {
        boolean needsUpdate = isUpdateRequired(appVersion, platform);
        String requiredVersion = getRequiredVersion(platform);

        String message = needsUpdate ?
            ErrorCode.UPDATE_REQUIRED.getMessage() :
            SuccessCode.UPDATE_NOT_REQUIRED.getMessage();

        return new VersionCheckResponse(needsUpdate, appVersion, requiredVersion, message);
    }

    /**
     * 업데이트 필요 여부 확인
     * 클라이언트 버전과 필수 버전을 비교하여 업데이트가 필요한지 확인합니다.
     *
     * @param appVersion 클라이언트의 앱 버전
     * @param platform 플랫폼 정보
     * @return 업데이트 필요 여부
     */
    private boolean isUpdateRequired(String appVersion, String platform) {
        String requiredVersion = getRequiredVersion(platform);
        return compareVersions(appVersion, requiredVersion) < 0;
    }

    /**
     * 플랫폼별 필수 버전 조회
     * properties에 설정된 플랫폼별 최소 버전을 반환합니다.
     *
     * @param platform 플랫폼 (android, ios)
     * @return 해당 플랫폼의 필수 버전
     * @throws RuntimeException 지원하지 않는 플랫폼일 경우
     */
    private String getRequiredVersion(String platform) {
        return switch (platform.toLowerCase()) {
            case "android" -> googlePlayStoreVersion;
            case "ios" -> appleAppStoreVersion;
            default -> throw new RuntimeException(ErrorCode.UNSUPPORTED_PLATFORM.getMessage());
        };
    }

    /**
     * 버전 비교
     * 두 버전 문자열을 비교하여 우선순위를 결정합니다.
     * 버전 형식: "major.minor.patch" (예: "1.2.3")
     *
     * @param version1 비교할 첫 번째 버전
     * @param version2 비교할 두 번째 버전
     * @return 비교 결과 (-1: version1이 낮음, 0: 동일, 1: version1이 높음)
     * @throws RuntimeException 잘못된 버전 형식일 경우
     */
    private int compareVersions(String version1, String version2) {
        try {
            String[] v1Parts = version1.split("\\.");
            String[] v2Parts = version2.split("\\.");

            int maxLength = Math.max(v1Parts.length, v2Parts.length);

            for (int i = 0; i < maxLength; i++) {
                int v1Part = i < v1Parts.length ? Integer.parseInt(v1Parts[i]) : 0;
                int v2Part = i < v2Parts.length ? Integer.parseInt(v2Parts[i]) : 0;

                if (v1Part < v2Part) {
                    return -1;
                } else if (v1Part > v2Part) {
                    return 1;
                }
            }

            return 0;
        } catch (NumberFormatException e) {
            throw new RuntimeException(ErrorCode.INVALID_VERSION_FORMAT.getMessage());
        }
    }
}