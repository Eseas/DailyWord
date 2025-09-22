package com.dailyword.file.application.usecase;

import com.dailyword.file.adapter.in.facade.dto.FileInfoResponse;
import com.dailyword.file.adapter.in.facade.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUsecase {

    FileUploadResponse uploadFile(MultipartFile file, Long uploaderId, String description, Boolean isPublic);

    FileInfoResponse getFileInfo(String fileRefCode);

    String generateDownloadUrl(String fileRefCode, Long requesterId);

    void deleteFile(String fileRefCode, Long requesterId);
}