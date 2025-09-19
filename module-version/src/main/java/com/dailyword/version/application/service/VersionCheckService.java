package com.dailyword.version.application.service;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.version.adapter.in.facade.dto.VersionCheckResponse;
import com.dailyword.version.application.usecase.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionCheckService implements VersionCheckUsecase {

    @Value("${app.version.google.playstore}")
    private String googlePlayStoreVersion;

    @Value("${app.version.apple.appstore}")
    private String appleAppStoreVersion;

    @Override
    public VersionCheckResponse checkVersion(String appVersion, String platform) {
        boolean needsUpdate = isUpdateRequired(appVersion, platform);
        String requiredVersion = getRequiredVersion(platform);

        String message = needsUpdate ?
            ErrorCode.UPDATE_REQUIRED.getMessage() :
            SuccessCode.UPDATE_NOT_REQUIRED.getMessage();

        return new VersionCheckResponse(needsUpdate, appVersion, requiredVersion, message);
    }

    private boolean isUpdateRequired(String appVersion, String platform) {
        String requiredVersion = getRequiredVersion(platform);
        return compareVersions(appVersion, requiredVersion) < 0;
    }

    private String getRequiredVersion(String platform) {
        return switch (platform.toLowerCase()) {
            case "android" -> googlePlayStoreVersion;
            case "ios" -> appleAppStoreVersion;
            default -> throw new RuntimeException(ErrorCode.UNSUPPORTED_PLATFORM.getMessage());
        };
    }

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