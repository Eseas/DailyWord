package com.dailyword.gateway.application.service.version;

import com.dailyword.gateway.dto.version.VersionCheckRequest;
import com.dailyword.gateway.dto.version.VersionCheckResponse;
import com.dailyword.gateway.adapter.out.client.VersionClient;
import com.dailyword.gateway.application.usecase.version.VersionCheckUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionCheckService implements VersionCheckUsecase {

    private final VersionClient versionClient;

    @Override
    public VersionCheckResponse checkVersion(VersionCheckRequest request) {
        return versionClient.checkVersion(request).getData();
    }
}