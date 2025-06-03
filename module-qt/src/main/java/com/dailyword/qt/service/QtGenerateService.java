package com.dailyword.qt.service;

import com.dailyword.qt.dto.BibleVerse;
import com.dailyword.qt.dto.QtSectionDto;
import com.dailyword.qt.repository.QtPassageRepository;
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

@Service
@RequiredArgsConstructor
public class QtGenerateService {

    private final WebClient openAiClient;
    private final QtPassageRepository qtPassageRepository;

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

    private void saveToDb(String book, Integer chapter, List<QtSectionDto> sections) {
        for (QtSectionDto dto : sections) {
            qtPassageRepository.save(dto.toEntity(book, chapter));
        }
    }
}
