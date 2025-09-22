package com.dailyword.file.adapter.in.facade.dto;

import com.dailyword.file.domain.model.File;
import com.dailyword.file.domain.model.FileStatus;
import com.dailyword.file.domain.model.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FileInfoResponse {
    private String fileRefCode;
    private String originalFileName;
    private String fileUrl;
    private Long fileSize;
    private FileType fileType;
    private FileStatus status;
    private String description;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public static FileInfoResponse from(File file) {
        return new FileInfoResponse(
            file.getRefCode(),
            file.getOriginalFileName(),
            file.getS3Url(),
            file.getFileSize(),
            file.getFileType(),
            file.getStatus(),
            file.getDescription(),
            file.getIsPublic(),
            file.getCreatedAt(),
            file.getExpiresAt()
        );
    }
}