package com.dailyword.file.application.service;

import com.dailyword.file.adapter.in.facade.dto.FileUploadResponse;
import com.dailyword.file.application.usecase.UploadFileUsecase;
import com.dailyword.file.domain.model.File;
import com.dailyword.file.infrastructure.s3.S3Service;
import com.dailyword.file.infrastructure.s3.S3UploadResult;
import com.dailyword.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 업로드 서비스
 * 파일의 업로드 비즈니스 로직을 담당합니다.
 * S3와 데이터베이스를 연동하여 파일을 저장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUsecase {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

    /**
     * 파일 업로드
     * MultipartFile을 S3에 업로드하고 파일 메타데이터를 데이터베이스에 저장합니다.
     * 파일 크기, 확장자 등의 유효성을 검증한 후 업로드를 진행합니다.
     *
     * @param multipartFile 업로드할 파일
     * @param uploaderId 파일을 업로드하는 사용자의 ID
     * @param description 파일에 대한 설명 (선택사항)
     * @param isPublic 파일 공개 여부
     * @return 업로드된 파일의 정보
     * @throws RuntimeException 파일 업로드 실패 시
     */
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

    /**
     * 파일 유효성 검증
     * 파일 존재 여부, 크기, 파일명, 확장자 등을 검증합니다.
     *
     * @param file 검증할 MultipartFile
     * @throws RuntimeException 유효하지 않은 파일일 경우
     */
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

    /**
     * 파일명에서 확장자 추출
     * 파일명에서 마지막 점(.) 이후의 확장자를 추출합니다.
     *
     * @param fileName 파일명
     * @return 파일 확장자 (점 포함)
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 위험한 파일 타입 확인
     * 실행 파일 등 보안상 위험한 파일 확장자인지 확인합니다.
     *
     * @param extension 확인할 파일 확장자
     * @return 위험한 파일 타입 여부
     */
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