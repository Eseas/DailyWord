package com.dailyword.qt.application.service;

import com.dailyword.qt.domain.model.entity.QtPassage;
import com.dailyword.qt.dto.QtSectionDto;
import com.dailyword.qt.infrastructure.db.repository.QtPassageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * QT 구절 영속성 서비스
 * QT 구절 데이터의 저장 및 관리를 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QtPassagePersistenceService {

    private final QtPassageRepository qtPassageRepository;

    /**
     * QT 섹션을 데이터베이스에 저장
     * QtSectionDto 리스트를 엔티티로 변환하여 저장합니다.
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @param sections 저장할 QT 섹션 리스트
     * @return 저장된 엔티티 리스트
     */
    @Transactional
    public List<QtPassage> saveQtSections(String book, Integer chapter, List<QtSectionDto> sections) {
        List<QtPassage> savedPassages = new ArrayList<>();

        for (QtSectionDto section : sections) {
            try {
                QtPassage passage = section.toEntity(book, chapter);
                QtPassage saved = qtPassageRepository.save(passage);
                savedPassages.add(saved);

                log.debug("Saved QT passage: {} {}:{}-{}",
                    book, chapter, section.getStart_verse(), section.getEnd_verse());
            } catch (Exception e) {
                log.error("Failed to save QT section: {} {}:{}-{}",
                    book, chapter, section.getStart_verse(), section.getEnd_verse(), e);
            }
        }

        log.info("Saved {} QT passages for {} chapter {}",
            savedPassages.size(), book, chapter);

        return savedPassages;
    }

    /**
     * 특정 책과 장의 기존 QT 구절 존재 여부 확인
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @return 기존 데이터 존재 여부
     */
    @Transactional(readOnly = true)
    public boolean existsByBookAndChapter(String book, Integer chapter) {
        // 추후 필요시 구현
        // return qtPassageRepository.existsByBookAndChapter(book, chapter);
        return false;
    }
}