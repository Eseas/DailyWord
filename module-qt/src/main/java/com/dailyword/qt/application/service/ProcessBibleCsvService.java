package com.dailyword.qt.application.service;

import com.dailyword.qt.application.usecase.GenerateQtContentUsecase;
import com.dailyword.qt.application.usecase.ParseBibleTextUsecase;
import com.dailyword.qt.application.usecase.ProcessBibleCsvUsecase;
import com.dailyword.qt.dto.BibleVerse;
import com.dailyword.qt.dto.QtSectionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * 성경 CSV 처리 서비스
 * CSV 파일에서 성경 데이터를 읽고 QT 섹션을 생성하는 전체 프로세스를 관리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessBibleCsvService implements ProcessBibleCsvUsecase {

    private final BibleCsvReaderService csvReaderService;
    private final ParseBibleTextUsecase parseBibleTextUsecase;
    private final GenerateQtContentUsecase generateQtContentUsecase;

    /**
     * 성경 CSV 파일 처리 및 QT 섹션 생성
     * CSV 파일을 읽고, 각 장별로 의미 단위로 분할하여 QT 섹션을 생성합니다.
     *
     * @param csvPath 처리할 성경 CSV 파일의 경로
     * @throws Exception CSV 파일 읽기 실패 또는 처리 실패 시
     */
    @Override
    public void processBibleCsv(Path csvPath) throws Exception {
        log.info("Starting to process Bible CSV from: {}", csvPath);

        // CSV 파일 읽기
        Map<String, Map<Integer, List<BibleVerse>>> bible = csvReaderService.readBibleCsv(csvPath);

        int totalProcessed = 0;
        int totalFailed = 0;

        // 각 책과 장에 대해 처리
        for (Map.Entry<String, Map<Integer, List<BibleVerse>>> bookEntry : bible.entrySet()) {
            String book = bookEntry.getKey();

            for (Map.Entry<Integer, List<BibleVerse>> chapterEntry : bookEntry.getValue().entrySet()) {
                Integer chapter = chapterEntry.getKey();
                List<BibleVerse> verses = chapterEntry.getValue();

                try {
                    log.debug("Processing {} chapter {}", book, chapter);

                    // 성경 본문을 의미 단위로 분할
                    List<QtSectionDto> sections = parseBibleTextUsecase.parseBibleText(book, chapter, verses);

                    // QT 콘텐츠 생성 및 저장
                    generateQtContentUsecase.generateAndSaveQtContent(book, chapter, sections);

                    totalProcessed++;
                    log.info("Successfully processed {} chapter {}", book, chapter);

                } catch (Exception e) {
                    totalFailed++;
                    log.error("Failed to process {} chapter {}", book, chapter, e);
                }
            }
        }

        log.info("Bible CSV processing completed. Processed: {}, Failed: {}",
            totalProcessed, totalFailed);

        if (totalFailed > 0) {
            log.warn("Some chapters failed to process. Please check the logs for details.");
        }
    }
}