package com.dailyword.file.application.service;

import com.dailyword.file.application.usecase.DeleteFileUsecase;
import com.dailyword.file.domain.model.File;
import com.dailyword.file.infrastructure.s3.S3Service;
import com.dailyword.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파일 삭제 서비스
 * 파일 삭제 비즈니스 로직을 담당합니다.
 * S3와 데이터베이스에서 파일을 삭제 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteFileService implements DeleteFileUsecase {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

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
}