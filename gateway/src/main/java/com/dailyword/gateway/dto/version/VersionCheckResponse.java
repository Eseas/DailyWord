package com.dailyword.gateway.dto.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VersionCheckResponse {
    private boolean needsUpdate;
    private String currentVersion;
    private String requiredVersion;
    private String message;
}