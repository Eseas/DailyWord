package com.dailyword.qt.application.service;

import com.dailyword.qt.dto.QtSectionDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * OpenAI API 클라이언트 서비스
 * OpenAI GPT API와의 통신을 담당합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiClientService {

    private final WebClient openAiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Duration API_TIMEOUT = Duration.ofSeconds(30);
    private static final String GPT_MODEL = "gpt-4";
    private static final double TEMPERATURE = 0.7;

    /**
     * GPT API 호출하여 QT 섹션 생성
     * 프롬프트를 GPT에 전송하고 응답을 QtSectionDto 리스트로 변환합니다.
     *
     * @param systemPrompt 시스템 프롬프트
     * @param userPrompt 사용자 프롬프트
     * @return 생성된 QT 섹션 리스트
     * @throws RuntimeException API 호출 실패 또는 응답 파싱 실패 시
     */
    public List<QtSectionDto> generateQtSections(String systemPrompt, String userPrompt) {
        Map<String, Object> requestBody = Map.of(
            "model", GPT_MODEL,
            "messages", List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userPrompt)
            ),
            "temperature", TEMPERATURE
        );

        try {
            JsonNode response = openAiClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .timeout(API_TIMEOUT)
                .onErrorResume(error -> {
                    log.error("OpenAI API call failed", error);
                    return Mono.error(new RuntimeException("OpenAI API 호출 실패", error));
                })
                .block();

            String content = extractContent(response);
            return parseQtSections(content);

        } catch (Exception e) {
            log.error("Failed to generate QT sections", e);
            throw new RuntimeException("QT 섹션 생성 실패", e);
        }
    }

    /**
     * API 응답에서 콘텐츠 추출
     *
     * @param response OpenAI API 응답
     * @return 응답 콘텐츠
     */
    private String extractContent(JsonNode response) {
        try {
            return response.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
        } catch (Exception e) {
            log.error("Failed to extract content from response: {}", response);
            throw new RuntimeException("응답에서 콘텐츠 추출 실패", e);
        }
    }

    /**
     * JSON 문자열을 QtSectionDto 리스트로 파싱
     *
     * @param jsonContent JSON 형식의 문자열
     * @return QtSectionDto 리스트
     */
    private List<QtSectionDto> parseQtSections(String jsonContent) {
        try {
            QtSectionDto[] sections = objectMapper.readValue(jsonContent, QtSectionDto[].class);
            return Arrays.asList(sections);
        } catch (Exception e) {
            log.error("Failed to parse QT sections from: {}", jsonContent);
            throw new RuntimeException("QT 섹션 파싱 실패", e);
        }
    }
}