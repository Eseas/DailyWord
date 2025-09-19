package com.dailyword.version.adapter.in.facade.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VersionCheckRequest {
    private String appVersion;
    private String platform; // "android" or "ios"
}