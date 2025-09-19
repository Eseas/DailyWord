package com.dailyword.version.application.usecase;

import com.dailyword.version.adapter.in.facade.dto.VersionCheckResponse;

public interface VersionCheckUsecase {
    VersionCheckResponse checkVersion(String appVersion, String platform);
}