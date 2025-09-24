package com.dailyword.qt.application.service;

import com.dailyword.qt.dto.BibleVerse;
import com.dailyword.qt.dto.QtSectionDto;
import com.dailyword.qt.infrastructure.db.repository.QtPassageRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

/**
 * QT 생성 서비스
 * 성경 본문을 AI를 활용하여 의미 단위로 분할하고 QT 섹션을 생성하는 비즈니스 로직을 담당합니다.
 * CSV 형태의 성경 데이터를 읽어 OpenAI GPT를 통해 의미 있는 단락으로 나누고 데이터베이스에 저장합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class QtGenerateService {

    private final WebClient openAiClient;
    private final QtPassageRepository qtPassageRepository;

    /**
     * 성경 CSV 파일 처리 및 QT 섹션 생성
     * CSV 파일에서 성경 본문을 읽어와 책별, 장별로 그룹화한 후
     * OpenAI GPT를 사용하여 의미 단위로 분할하고 QT 섹션을 생성합니다.
     *
     * @param csvPath 처리할 성경 CSV 파일의 경로
     * @throws Exception CSV 파일 읽기 실패 또는 AI 처리 실패 시
     */
    public void processBibleCsv(Path csvPath) throws Exception {
        Map<String, Map<Integer, List<BibleVerse>>> bible = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvPath.toFile()))) {
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                BibleVerse verse = new BibleVerse(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3]);
                bible.computeIfAbsent(verse.book(), b -> new HashMap<>())
                        .computeIfAbsent(verse.chapter(), c -> new ArrayList<>())
                        .add(verse);
            }
        }

        for (String book : bible.keySet()) {
            for (Integer chapter : bible.get(book).keySet()) {
                List<BibleVerse> verses = bible.get(book).get(chapter);
                String prompt = createPrompt(book, chapter, verses);
                List<QtSectionDto> sections = callGpt(prompt);
                saveToDb(book, chapter, sections);
            }
        }
    }

    /**
     * OpenAI GPT를 위한 프롬프트 생성
     * 성경 본문을 GPT가 의미 단위로 분할할 수 있도록 구조화된 프롬프트를 생성합니다.
     * 응답 형식과 함께 처리할 성경 본문을 포함합니다.
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @param verses 해당 장의 모든 절 목록
     * @return GPT API 호출을 위한 구조화된 프롬프트
     */
    private String createPrompt(String book, int chapter, List<BibleVerse> verses) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("다음은 %s %d장의 성경 본문입니다. 의미 단위로 분할하고, 각 단락에 그룹 정보를 포함해주세요.\n\n", book, chapter));
        sb.append("형식 예시:\n");
        sb.append("[{ \"group_id\": \"SOMETHING\", \"group_title\": \"어떤 이야기\", \"group_order\": 1, \"start_verse\": 1, \"end_verse\": 5, \"title\": \"...\", \"summary\": \"...\", \"tags\": [\"...\"] }, ...]\n\n");
        sb.append("본문:\n");

        for (BibleVerse verse : verses) {
            sb.append(verse.verse()).append("절: ").append(verse.text()).append("\n");
        }

        return sb.toString();
    }

    /**
     * OpenAI GPT API 호출
     * 생성된 프롬프트를 GPT-4 모델에 전송하여 의미 단위로 분할된 QT 섹션을 생성합니다.
     * JSON 형태의 응답을 QtSectionDto 객체 리스트로 파싱합니다.
     *
     * @param prompt GPT에 전송할 프롬프트
     * @return 생성된 QT 섹션 리스트
     * @throws RuntimeException GPT 응답 파싱 실패 시
     */
    private List<QtSectionDto> callGpt(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4",
                "messages", List.of(
                        Map.of("role", "system", "content", "너는 성경 큐티를 의미 단위로 분할하는 도우미야."),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        String response = openAiClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block()
                .get("choices").get(0).get("message").get("content").asText();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(response, QtSectionDto[].class));
        } catch (Exception e) {
            throw new RuntimeException("GPT 응답 파싱 실패: " + response, e);
        }
    }

    /**
     * 생성된 QT 섹션을 데이터베이스에 저장
     * GPT로부터 생성된 QT 섹션 정보를 엔티티로 변환하여 데이터베이스에 저장합니다.
     *
     * @param book 성경책 이름
     * @param chapter 장 번호
     * @param sections 저장할 QT 섹션 리스트
     */
    private void saveToDb(String book, Integer chapter, List<QtSectionDto> sections) {
        for (QtSectionDto dto : sections) {
            qtPassageRepository.save(dto.toEntity(book, chapter));
        }
    }
}
