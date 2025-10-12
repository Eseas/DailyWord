package com.dailyword.qt.application.service;

import com.dailyword.qt.application.usecase.ParseBibleTextUsecase;
import com.dailyword.qt.dto.BibleVerse;
import com.dailyword.qt.dto.QtSectionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 성경 본문 파싱 서비스
 * GPT를 활용하여 성경 본문을 의미 단위로 분할합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ParseBibleTextService implements ParseBibleTextUsecase {

    private final GptPromptService promptService;
    private final OpenAiClientService openAiClientService;

    /**
     * 성경 본문을 의미 단위로 분할
     * GPT를 사용하여 성경 본문을 의미 있는 단락으로 나눕니다.
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @param verses 해당 장의 모든 절 목록
     * @return 분할된 QT 섹션 리스트
     */
    @Override
    public List<QtSectionDto> parseBibleText(String book, int chapter, List<BibleVerse> verses) {
        log.debug("Parsing Bible text for {} chapter {} with {} verses",
            book, chapter, verses.size());

        // 프롬프트 생성
        String systemPrompt = promptService.getSystemPrompt();
        String userPrompt = promptService.createPrompt(book, chapter, verses);

        // GPT를 통해 의미 단위로 분할
        List<QtSectionDto> sections = openAiClientService.generateQtSections(systemPrompt, userPrompt);

        log.info("Successfully parsed {} chapter {} into {} sections",
            book, chapter, sections.size());

        return sections;
    }
}