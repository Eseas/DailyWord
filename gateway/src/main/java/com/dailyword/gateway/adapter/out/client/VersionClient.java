package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.version.VersionCheckRequest;
import com.dailyword.gateway.dto.version.VersionCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "versionClient", url = "${module.version.url}")
public interface VersionClient {

    @PostMapping("/internal/version/check")
    APIResponse<VersionCheckResponse> checkVersion(@RequestBody VersionCheckRequest request);
}