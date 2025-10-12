package com.dailyword.file.application.usecase;

import com.dailyword.file.adapter.in.facade.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUsecase {
    FileUploadResponse uploadFile(MultipartFile file, Long uploaderId, String description, Boolean isPublic);
}