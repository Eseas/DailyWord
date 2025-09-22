package com.dailyword.gateway.application.service.file;

import com.dailyword.gateway.adapter.out.client.FileClient;
import com.dailyword.gateway.application.usecase.file.FileUsecase;
import com.dailyword.gateway.dto.file.FileInfoResponse;
import com.dailyword.gateway.dto.file.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService implements FileUsecase {

    private final FileClient fileClient;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file, Long uploaderId, String description, Boolean isPublic) {
        try {
            return fileClient.uploadFile(file, uploaderId, description, isPublic).getData();
        } catch (Exception e) {
            log.error("Failed to upload file for user: {}", uploaderId, e);
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    @Override
    public FileInfoResponse getFileInfo(String fileRefCode) {
        try {
            return fileClient.getFileInfo(fileRefCode).getData();
        } catch (Exception e) {
            log.error("Failed to get file info: {}", fileRefCode, e);
            throw new RuntimeException("파일 정보 조회에 실패했습니다.", e);
        }
    }

    @Override
    public String generateDownloadUrl(String fileRefCode, Long requesterId) {
        try {
            return fileClient.generateDownloadUrl(fileRefCode, requesterId).getData();
        } catch (Exception e) {
            log.error("Failed to generate download URL for file: {} by user: {}", fileRefCode, requesterId, e);
            throw new RuntimeException("다운로드 URL 생성에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteFile(String fileRefCode, Long requesterId) {
        try {
            fileClient.deleteFile(fileRefCode, requesterId);
            log.info("File deleted successfully: {} by user: {}", fileRefCode, requesterId);
        } catch (Exception e) {
            log.error("Failed to delete file: {} by user: {}", fileRefCode, requesterId, e);
            throw new RuntimeException("파일 삭제에 실패했습니다.", e);
        }
    }
}