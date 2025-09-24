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

/**
 * 파일 업로드 서비스
 * 파일의 업로드, 조회, 다운로드 URL 생성, 삭제 등의 비즈니스 로직을 담당합니다.
 * S3와 데이터베이스를 연동하여 파일을 관리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService implements FileUploadUsecase {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final Duration DOWNLOAD_URL_EXPIRATION = Duration.ofHours(1);

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
     * 파일 정보 조회
     * 파일 참조 코드를 통해 파일의 상세 정보를 조회합니다.
     *
     * @param fileRefCode 조회할 파일의 참조 코드
     * @return 파일의 상세 정보
     * @throws RuntimeException 파일이 존재하지 않을 경우
     */
    @Override
    @Transactional(readOnly = true)
    public FileInfoResponse getFileInfo(String fileRefCode) {
        File file = fileRepository.findByRefCode(fileRefCode)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        return FileInfoResponse.from(file);
    }

    /**
     * 파일 다운로드 URL 생성
     * 파일에 접근할 수 있는 Presigned URL을 생성합니다.
     * 파일 접근 권한을 확인하고 파일 상태를 검증한 후 URL을 생성합니다.
     *
     * @param fileRefCode 다운로드할 파일의 참조 코드
     * @param requesterId 다운로드를 요청하는 사용자의 ID
     * @return S3 Presigned URL (1시간 동안 유효)
     * @throws RuntimeException 파일이 존재하지 않거나 접근 권한이 없을 경우
     */
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

    /**
     * 파일 삭제
     * S3에서 파일을 삭제하고 데이터베이스의 파일 상태를 DELETED로 변경합니다.
     * 파일 업로더만 삭제할 수 있습니다.
     *
     * @param fileRefCode 삭제할 파일의 참조 코드
     * @param requesterId 삭제를 요청하는 사용자의 ID
     * @throws RuntimeException 파일이 존재하지 않거나 삭제 권한이 없을 경우
     */
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
     * 파일 접근 권한 확인
     * 공개 파일이거나 업로더 본인인 경우 접근을 허용합니다.
     *
     * @param file 접근하려는 파일
     * @param requesterId 접근을 요청하는 사용자의 ID
     * @return 접근 가능 여부
     */
    private boolean canAccessFile(File file, Long requesterId) {
        // 공개 파일이거나 업로더 본인인 경우 접근 가능
        return file.getIsPublic() || file.getUploaderId().equals(requesterId);
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