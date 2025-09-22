package com.dailyword.file.infrastructure.s3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class S3UploadResult {
    private String originalFileName;
    private String storedFileName;
    private String fileUrl;
    private Long fileSize;
    private String mimeType;
    private String bucketName;
}