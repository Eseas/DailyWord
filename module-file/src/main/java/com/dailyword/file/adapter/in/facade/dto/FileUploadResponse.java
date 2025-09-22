package com.dailyword.file.adapter.in.facade.dto;

import com.dailyword.file.domain.model.FileStatus;
import com.dailyword.file.domain.model.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResponse {
    private String fileRefCode;
    private String originalFileName;
    private String fileUrl;
    private Long fileSize;
    private FileType fileType;
    private FileStatus status;
    private String message;

    public static FileUploadResponse success(String fileRefCode, String originalFileName,
                                           String fileUrl, Long fileSize, FileType fileType) {
        return new FileUploadResponse(fileRefCode, originalFileName, fileUrl, fileSize,
                                    fileType, FileStatus.ACTIVE, "파일 업로드가 완료되었습니다.");
    }

    public static FileUploadResponse pending(String fileRefCode, String originalFileName,
                                           Long fileSize, FileType fileType) {
        return new FileUploadResponse(fileRefCode, originalFileName, null, fileSize,
                                    fileType, FileStatus.PENDING, "파일 업로드 처리 중입니다.");
    }
}