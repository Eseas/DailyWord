package com.dailyword.qt.application.service;

import com.dailyword.qt.dto.BibleVerse;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

/**
 * 성경 CSV 파일 읽기 서비스
 * CSV 형태의 성경 데이터를 읽어 구조화된 데이터로 변환합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
public class BibleCsvReaderService {

    /**
     * CSV 파일에서 성경 데이터 읽기
     * CSV 파일을 파싱하여 책별, 장별로 그룹화된 성경 구절 맵을 반환합니다.
     *
     * @param csvPath CSV 파일 경로
     * @return 책별, 장별로 그룹화된 성경 구절 맵
     * @throws Exception CSV 파일 읽기 실패 시
     */
    public Map<String, Map<Integer, List<BibleVerse>>> readBibleCsv(Path csvPath) throws Exception {
        Map<String, Map<Integer, List<BibleVerse>>> bible = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvPath.toFile()))) {
            String[] line;
            reader.readNext(); // skip header

            while ((line = reader.readNext()) != null) {
                if (line.length < 4) {
                    log.warn("Invalid CSV line skipped: {}", Arrays.toString(line));
                    continue;
                }

                try {
                    BibleVerse verse = new BibleVerse(
                        line[0],
                        Integer.parseInt(line[1]),
                        Integer.parseInt(line[2]),
                        line[3]
                    );

                    bible.computeIfAbsent(verse.book(), b -> new HashMap<>())
                         .computeIfAbsent(verse.chapter(), c -> new ArrayList<>())
                         .add(verse);

                } catch (NumberFormatException e) {
                    log.warn("Failed to parse verse numbers in line: {}", Arrays.toString(line), e);
                }
            }
        }

        log.info("Successfully read {} books from CSV", bible.size());
        return bible;
    }
}