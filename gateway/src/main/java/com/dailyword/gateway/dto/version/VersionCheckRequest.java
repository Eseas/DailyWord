package com.dailyword.gateway.dto.version;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VersionCheckRequest {
    private String appVersion;
    private String platform;
}