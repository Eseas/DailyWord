package com.dailyword.file.application.service;

import com.dailyword.file.adapter.in.facade.dto.FileInfoResponse;
import com.dailyword.file.application.usecase.GetFileInfoUsecase;
import com.dailyword.file.domain.model.File;
import com.dailyword.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파일 정보 조회 서비스
 * 파일의 메타데이터 조회 비즈니스 로직을 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetFileInfoService implements GetFileInfoUsecase {

    private final FileRepository fileRepository;

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
}