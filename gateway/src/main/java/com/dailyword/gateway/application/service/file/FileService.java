package com.dailyword.gateway.application.service.file;

import com.dailyword.gateway.adapter.out.client.FileClient;
import com.dailyword.gateway.application.usecase.file.FileUsecase;
import com.dailyword.gateway.dto.file.FileInfoResponse;
import com.dailyword.gateway.dto.file.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관리 서비스 (Gateway)
 * Gateway 모듈에서 파일 관련 비즈니스 로직을 담당하며, module-file과의 통신을 처리합니다.
 * Feign Client를 통해 module-file의 내부 API를 호출하여 파일 업로드, 조회, 다운로드, 삭제 기능을 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService implements FileUsecase {

    private final FileClient fileClient;

    /**
     * 파일 업로드
     * module-file의 내부 API를 통해 파일을 업로드합니다.
     * MultipartFile을 받아 S3에 업로드하고 파일 메타데이터를 데이터베이스에 저장합니다.
     *
     * @param file 업로드할 파일 (MultipartFile)
     * @param uploaderId 파일을 업로드하는 사용자의 ID
     * @param description 파일에 대한 설명 (선택사항)
     * @param isPublic 파일 공개 여부 (기본값: false)
     * @return 업로드된 파일의 정보 (fileRefCode, 파일명, URL 등)
     * @throws RuntimeException 파일 업로드 실패 시
     */
    @Override
    public FileUploadResponse uploadFile(MultipartFile file, Long uploaderId, String description, Boolean isPublic) {
        try {
            return fileClient.uploadFile(file, uploaderId, description, isPublic).getData();
        } catch (Exception e) {
            log.error("Failed to upload file for user: {}", uploaderId, e);
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    /**
     * 파일 정보 조회
     * 파일 참조 코드를 통해 파일의 상세 정보를 조회합니다.
     * module-file의 내부 API를 호출하여 파일 메타데이터를 가져옵니다.
     *
     * @param fileRefCode 조회할 파일의 참조 코드
     * @return 파일의 상세 정보 (파일명, 크기, 타입, 생성일시 등)
     * @throws RuntimeException 파일 정보 조회 실패 시
     */
    @Override
    public FileInfoResponse getFileInfo(String fileRefCode) {
        try {
            return fileClient.getFileInfo(fileRefCode).getData();
        } catch (Exception e) {
            log.error("Failed to get file info: {}", fileRefCode, e);
            throw new RuntimeException("파일 정보 조회에 실패했습니다.", e);
        }
    }

    /**
     * 파일 다운로드 URL 생성
     * 파일에 접근할 수 있는 Presigned URL을 생성합니다.
     * module-file을 통해 권한 검증 후 1시간 동안 유효한 다운로드 링크를 제공합니다.
     *
     * @param fileRefCode 다운로드할 파일의 참조 코드
     * @param requesterId 다운로드를 요청하는 사용자의 ID
     * @return S3 Presigned URL (1시간 동안 유효한 다운로드 링크)
     * @throws RuntimeException 다운로드 URL 생성 실패 시
     */
    @Override
    public String generateDownloadUrl(String fileRefCode, Long requesterId) {
        try {
            return fileClient.generateDownloadUrl(fileRefCode, requesterId).getData();
        } catch (Exception e) {
            log.error("Failed to generate download URL for file: {} by user: {}", fileRefCode, requesterId, e);
            throw new RuntimeException("다운로드 URL 생성에 실패했습니다.", e);
        }
    }

    /**
     * 파일 삭제
     * 파일을 삭제합니다. module-file을 통해 파일 업로더만 삭제할 수 있도록 권한을 검증합니다.
     * S3에서 실제 파일을 삭제하고 데이터베이스 상태를 변경합니다.
     *
     * @param fileRefCode 삭제할 파일의 참조 코드
     * @param requesterId 삭제를 요청하는 사용자의 ID (업로더와 일치해야 함)
     * @throws RuntimeException 파일 삭제 실패 시
     */
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