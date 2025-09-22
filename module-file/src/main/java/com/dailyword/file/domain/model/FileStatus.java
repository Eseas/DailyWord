package com.dailyword.file.domain.model;

public enum FileStatus {
    PENDING("업로드 대기"),
    ACTIVE("활성"),
    DELETED("삭제됨"),
    EXPIRED("만료됨"),
    CORRUPTED("손상됨");

    private final String description;

    FileStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}