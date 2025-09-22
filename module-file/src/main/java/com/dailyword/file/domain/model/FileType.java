package com.dailyword.file.domain.model;

public enum FileType {
    IMAGE("이미지"),
    DOCUMENT("문서"),
    VIDEO("비디오"),
    AUDIO("오디오"),
    ARCHIVE("압축파일"),
    OTHER("기타");

    private final String description;

    FileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FileType fromMimeType(String mimeType) {
        if (mimeType == null) return OTHER;

        if (mimeType.startsWith("image/")) return IMAGE;
        if (mimeType.startsWith("video/")) return VIDEO;
        if (mimeType.startsWith("audio/")) return AUDIO;
        if (mimeType.equals("application/pdf") ||
            mimeType.startsWith("application/msword") ||
            mimeType.startsWith("application/vnd.openxmlformats")) return DOCUMENT;
        if (mimeType.equals("application/zip") ||
            mimeType.equals("application/x-rar-compressed")) return ARCHIVE;

        return OTHER;
    }
}