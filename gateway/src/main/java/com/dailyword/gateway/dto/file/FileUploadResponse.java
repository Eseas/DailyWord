package com.dailyword.gateway.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private String fileRefCode;
    private String originalFileName;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private String status;
    private String message;
}