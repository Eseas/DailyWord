package com.dailyword.gateway.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoResponse {
    private String fileRefCode;
    private String originalFileName;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private String status;
    private String description;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}