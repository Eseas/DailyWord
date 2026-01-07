package com.dailyword.post.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportStatus {

    APPLICATION("신청 상태"),
    PROCESS("처리된 상태"),
    REJECT("반려된 상태");

    private final String description;
}
