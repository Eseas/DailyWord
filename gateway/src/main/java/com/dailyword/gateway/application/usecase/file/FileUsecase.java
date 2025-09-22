package com.dailyword.gateway.application.usecase.file;

import com.dailyword.gateway.dto.file.FileInfoResponse;
import com.dailyword.gateway.dto.file.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUsecase {

    FileUploadResponse uploadFile(MultipartFile file, Long uploaderId, String description, Boolean isPublic);

    FileInfoResponse getFileInfo(String fileRefCode);

    String generateDownloadUrl(String fileRefCode, Long requesterId);

    void deleteFile(String fileRefCode, Long requesterId);
}