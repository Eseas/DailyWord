package com.dailyword.version.adapter.in.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VersionCheckResponse {
    private boolean needsUpdate;
    private String currentVersion;
    private String requiredVersion;
    private String message;
}