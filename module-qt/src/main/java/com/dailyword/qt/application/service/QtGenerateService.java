package com.dailyword.qt.application.service;

import com.dailyword.qt.application.usecase.ProcessBibleCsvUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * QT 생성 서비스 (Facade)
 * 성경 CSV 파일 처리를 위한 Facade 서비스입니다.
 * 실제 처리는 ProcessBibleCsvUsecase에 위임합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 * @deprecated Use ProcessBibleCsvUsecase directly
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Deprecated(since = "2.0", forRemoval = true)
public class QtGenerateService {

    private final ProcessBibleCsvUsecase processBibleCsvUsecase;

    /**
     * 성경 CSV 파일 처리 (Facade)
     * ProcessBibleCsvUsecase에 처리를 위임합니다.
     *
     * @param csvPath 처리할 성경 CSV 파일의 경로
     * @throws Exception CSV 파일 읽기 실패 또는 처리 실패 시
     */
    public void processBibleCsv(Path csvPath) throws Exception {
        log.info("QtGenerateService is deprecated. Using ProcessBibleCsvUsecase instead.");
        processBibleCsvUsecase.processBibleCsv(csvPath);
    }
}
