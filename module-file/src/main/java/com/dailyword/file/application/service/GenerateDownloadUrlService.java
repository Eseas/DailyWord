package com.dailyword.file.application.service;

import com.dailyword.file.application.usecase.GenerateDownloadUrlUsecase;
import com.dailyword.file.domain.model.File;
import com.dailyword.file.domain.model.FileStatus;
import com.dailyword.file.infrastructure.s3.S3Service;
import com.dailyword.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * 파일 다운로드 URL 생성 서비스
 * 파일에 대한 Presigned URL 생성 비즈니스 로직을 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateDownloadUrlService implements GenerateDownloadUrlUsecase {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    private static final Duration DOWNLOAD_URL_EXPIRATION = Duration.ofHours(1);

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
}