package com.dailyword.file.application.service;

import com.dailyword.common.response.ErrorCode;
import com.dailyword.file.adapter.in.facade.dto.FileInfoResponse;
import com.dailyword.file.adapter.in.facade.dto.FileUploadResponse;
import com.dailyword.file.application.usecase.FileUploadUsecase;
import com.dailyword.file.domain.model.File;
import com.dailyword.file.domain.model.FileStatus;
import com.dailyword.file.infrastructure.s3.S3Service;
import com.dailyword.file.infrastructure.s3.S3UploadResult;
import com.dailyword.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService implements FileUploadUsecase {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final Duration DOWNLOAD_URL_EXPIRATION = Duration.ofHours(1);

    @Override
    @Transactional
    public FileUploadResponse uploadFile(MultipartFile multipartFile, Long uploaderId,
                                       String description, Boolean isPublic) {
        validateFile(multipartFile);

        try {
            // S3에 파일 업로드
            S3UploadResult s3Result = s3Service.uploadFile(multipartFile, "uploads");

            // 파일 엔티티 생성 및 저장
            File file = File.create(
                s3Result.getOriginalFileName(),
                s3Result.getStoredFileName(),
                getFileExtension(s3Result.getOriginalFileName()),
                s3Result.getFileSize(),
                s3Result.getMimeType(),
                s3Result.getFileUrl(),
                s3Result.getBucketName(),
                uploaderId,
                description,
                isPublic != null ? isPublic : false
            );

            file.activate(); // 업로드 완료 후 활성화
            File savedFile = fileRepository.save(file);

            log.info("File uploaded successfully: {} by user: {}",
                    savedFile.getRefCode(), uploaderId);

            return FileUploadResponse.success(
                savedFile.getRefCode(),
                savedFile.getOriginalFileName(),
                savedFile.getS3Url(),
                savedFile.getFileSize(),
                savedFile.getFileType()
            );

        } catch (Exception e) {
            log.error("File upload failed for user: {}", uploaderId, e);
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileInfoResponse getFileInfo(String fileRefCode) {
        File file = fileRepository.findByRefCode(fileRefCode)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        return FileInfoResponse.from(file);
    }

    @Override
    @Transactional(readOnly = true)
    public String generateDownloadUrl(String fileRefCode, Long requesterId) {
        File file = fileRepository.findByRefCode(fileRefCode)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        // 파일 접근 권한 검증
        if (!canAccessFile(file, requesterId)) {
            throw new RuntimeException("파일에 접근할 권한이 없습니다.");
        }

        if (file.getStatus() != FileStatus.ACTIVE) {
            throw new RuntimeException("사용할 수 없는 파일입니다.");
        }

        if (file.isExpired()) {
            throw new RuntimeException("만료된 파일입니다.");
        }

        return s3Service.generatePresignedDownloadUrl(file.getStoredFileName(), DOWNLOAD_URL_EXPIRATION);
    }

    @Override
    @Transactional
    public void deleteFile(String fileRefCode, Long requesterId) {
        File file = fileRepository.findByRefCode(fileRefCode)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        // 파일 삭제 권한 검증 (업로더만 삭제 가능)
        if (!file.getUploaderId().equals(requesterId)) {
            throw new RuntimeException("파일을 삭제할 권한이 없습니다.");
        }

        try {
            // S3에서 파일 삭제
            s3Service.deleteFile(file.getStoredFileName());

            // 파일 상태를 삭제로 변경
            file.delete();
            fileRepository.save(file);

            log.info("File deleted successfully: {} by user: {}", fileRefCode, requesterId);

        } catch (Exception e) {
            log.error("File deletion failed: {} by user: {}", fileRefCode, requesterId, e);
            throw new RuntimeException("파일 삭제에 실패했습니다.", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("업로드할 파일이 없습니다.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("파일 크기는 50MB를 초과할 수 없습니다.");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new RuntimeException("파일명이 유효하지 않습니다.");
        }

        // 위험한 파일 확장자 차단
        String fileExtension = getFileExtension(originalFileName).toLowerCase();
        if (isDangerousFileType(fileExtension)) {
            throw new RuntimeException("지원하지 않는 파일 형식입니다.");
        }
    }

    private boolean canAccessFile(File file, Long requesterId) {
        // 공개 파일이거나 업로더 본인인 경우 접근 가능
        return file.getIsPublic() || file.getUploaderId().equals(requesterId);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private boolean isDangerousFileType(String extension) {
        String[] dangerousExtensions = {".exe", ".bat", ".cmd", ".scr", ".pif", ".jar", ".com"};
        for (String dangerous : dangerousExtensions) {
            if (extension.equals(dangerous)) {
                return true;
            }
        }
        return false;
    }
}