package com.dailyword.file.application.service;

import com.dailyword.file.adapter.in.facade.dto.FileInfoResponse;
import com.dailyword.file.adapter.in.facade.dto.FileUploadResponse;
import com.dailyword.file.application.usecase.FileUploadUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관리 Facade 서비스
 * 파일의 업로드, 조회, 다운로드 URL 생성, 삭제 기능을 통합하여 제공합니다.
 * 각 기능별 서비스를 호출하는 Facade 역할을 수행합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService implements FileUploadUsecase {

    private final UploadFileService uploadFileService;

    /**
     * 파일 업로드 (Facade)
     * UploadFileService를 호출하여 파일 업로드를 처리합니다.
     *
     * @param multipartFile 업로드할 파일
     * @param uploaderId 파일을 업로드하는 사용자의 ID
     * @param description 파일에 대한 설명 (선택사항)
     * @param isPublic 파일 공개 여부
     * @return 업로드된 파일의 정보
     */
    @Override
    public FileUploadResponse uploadFile(MultipartFile multipartFile, Long uploaderId,
                                       String description, Boolean isPublic) {
        return uploadFileService.uploadFile(multipartFile, uploaderId, description, isPublic);
    }
}