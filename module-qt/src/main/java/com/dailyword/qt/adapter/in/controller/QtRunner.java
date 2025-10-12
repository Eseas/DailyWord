package com.dailyword.qt.adapter.in.controller;

import com.dailyword.qt.application.usecase.ProcessBibleCsvUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * QT 처리 실행기
 * 애플리케이션 시작 시 성경 CSV 파일을 처리하는 CommandLineRunner입니다.
 * qt.runner.enabled 속성을 통해 활성화/비활성화 할 수 있습니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    prefix = "qt.runner",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class QtRunner implements CommandLineRunner {

    private final ProcessBibleCsvUsecase processBibleCsvUsecase;

    @Value("${qt.csv.path:bible.csv}")
    private String csvPath;

    @Value("${qt.runner.skip-if-not-exists:true}")
    private boolean skipIfNotExists;

    @Override
    public void run(String... args) throws Exception {
        log.info("QtRunner started with CSV path: {}", csvPath);

        Path path = Paths.get(csvPath);

        // 파일 존재 여부 확인
        if (!Files.exists(path)) {
            if (skipIfNotExists) {
                log.warn("CSV file not found at: {}. Skipping processing.", csvPath);
                return;
            } else {
                throw new IllegalArgumentException("CSV file not found at: " + csvPath);
            }
        }

        try {
            log.info("Processing Bible CSV file from: {}", path);
            processBibleCsvUsecase.processBibleCsv(path);
            log.info("Bible CSV processing completed successfully");
        } catch (Exception e) {
            log.error("Failed to process Bible CSV file", e);
            throw e;
        }
    }
}