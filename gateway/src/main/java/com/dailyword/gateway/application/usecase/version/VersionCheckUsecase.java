package com.dailyword.gateway.application.usecase.version;

import com.dailyword.gateway.dto.version.VersionCheckRequest;
import com.dailyword.gateway.dto.version.VersionCheckResponse;

public interface VersionCheckUsecase {
    VersionCheckResponse checkVersion(VersionCheckRequest request);
}