package com.dailyword.qt.application.service;

import com.dailyword.qt.dto.BibleVerse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GPT 프롬프트 생성 서비스
 * OpenAI GPT API 호출을 위한 프롬프트를 생성합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
public class GptPromptService {

    private static final String PROMPT_TEMPLATE = """
        다음은 %s %d장의 성경 본문입니다. 의미 단위로 분할하고, 각 단락에 그룹 정보를 포함해주세요.

        형식 예시:
        [{ "group_id": "SOMETHING", "group_title": "어떤 이야기", "group_order": 1, "start_verse": 1, "end_verse": 5, "title": "...", "summary": "...", "tags": ["..."] }, ...]

        본문:
        %s
        """;

    private static final String SYSTEM_PROMPT = "너는 성경 큐티를 의미 단위로 분할하는 도우미야.";

    /**
     * GPT 프롬프트 생성
     * 성경 본문을 GPT가 의미 단위로 분할할 수 있도록 구조화된 프롬프트를 생성합니다.
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @param verses 해당 장의 모든 절 목록
     * @return GPT API 호출을 위한 구조화된 프롬프트
     */
    public String createPrompt(String book, int chapter, List<BibleVerse> verses) {
        StringBuilder versesText = new StringBuilder();
        for (BibleVerse verse : verses) {
            versesText.append(verse.verse())
                      .append("절: ")
                      .append(verse.text())
                      .append("\n");
        }

        return String.format(PROMPT_TEMPLATE, book, chapter, versesText.toString());
    }

    /**
     * 시스템 프롬프트 반환
     *
     * @return GPT 시스템 프롬프트
     */
    public String getSystemPrompt() {
        return SYSTEM_PROMPT;
    }
}