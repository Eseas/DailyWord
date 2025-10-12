package com.dailyword.qt.application.service;

import com.dailyword.qt.application.usecase.GenerateQtContentUsecase;
import com.dailyword.qt.dto.QtSectionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT 콘텐츠 생성 서비스
 * QT 섹션 정보를 받아 데이터베이스에 저장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateQtContentService implements GenerateQtContentUsecase {

    private final QtPassagePersistenceService persistenceService;

    /**
     * QT 콘텐츠 생성 및 저장
     * GPT로부터 생성된 QT 섹션을 데이터베이스에 저장합니다.
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @param sections 저장할 QT 섹션 리스트
     */
    @Override
    public void generateAndSaveQtContent(String book, Integer chapter, List<QtSectionDto> sections) {
        log.debug("Generating QT content for {} chapter {} with {} sections",
            book, chapter, sections.size());

        // 기존 데이터 확인 (필요시 중복 방지 로직 추가)
        if (persistenceService.existsByBookAndChapter(book, chapter)) {
            log.warn("QT content already exists for {} chapter {}. Skipping...", book, chapter);
            return;
        }

        // QT 섹션 저장
        persistenceService.saveQtSections(book, chapter, sections);

        log.info("Successfully generated and saved QT content for {} chapter {}",
            book, chapter);
    }
}