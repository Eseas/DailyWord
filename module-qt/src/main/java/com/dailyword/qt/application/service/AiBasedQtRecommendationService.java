package com.dailyword.qt.application.service;

import com.dailyword.qt.adapter.in.dto.AiQtRecommendationRequest;
import com.dailyword.qt.adapter.in.dto.QtRecommendationResponse;
import com.dailyword.qt.application.usecase.RecommendQtByAiUsecase;
import com.dailyword.qt.domain.model.entity.QtPassage;
import com.dailyword.qt.infrastructure.db.repository.QtPassageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AI 기반 QT 추천 서비스
 * 사용자의 응답과 감정 상태를 분석하여 적절한 말씀을 추천합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiBasedQtRecommendationService implements RecommendQtByAiUsecase {

    private final OpenAiClientService openAiClientService;
    private final GptPromptService gptPromptService;
    private final QtPassageRepository qtPassageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public QtRecommendationResponse recommendByAi(AiQtRecommendationRequest request) {
        log.info("Starting AI-based QT recommendation for {} responses",
                request.getUserResponses().size());

        try {
            // 1. 사용자 응답을 분석하여 키워드/태그 추출
            List<String> extractedTags = extractTagsFromResponses(request);
            log.debug("Extracted tags: {}", extractedTags);

            // 2. AI를 통해 추천 말씀 선정
            List<RecommendedPassageInfo> recommendedInfos = getAiRecommendations(request, extractedTags);

            // 3. 데이터베이스에서 실제 말씀 조회
            List<QtRecommendationResponse.RecommendedPassage> recommendations =
                findAndBuildRecommendedPassages(recommendedInfos, request.getRecommendationCount());

            // 4. 추천 이유 생성
            String reason = generateRecommendationReason(request, extractedTags);

            return new QtRecommendationResponse(
                recommendations,
                reason,
                QtRecommendationResponse.RecommendationType.AI_BASED
            );

        } catch (Exception e) {
            log.error("Failed to generate AI-based recommendations", e);
            throw new RuntimeException("AI 기반 추천 생성 실패", e);
        }
    }

    /**
     * 사용자 응답에서 키워드/태그 추출
     */
    private List<String> extractTagsFromResponses(AiQtRecommendationRequest request) {
        String prompt = createTagExtractionPrompt(request);
        String systemPrompt = "너는 사용자의 감정과 상황을 분석하여 관련된 성경 키워드를 추출하는 전문가야.";

        try {
            // GPT를 사용하여 태그 추출
            String response = callGptForTagExtraction(systemPrompt, prompt);
            return parseTagsFromResponse(response);
        } catch (Exception e) {
            log.warn("Failed to extract tags using AI, using fallback method", e);
            return getFallbackTags(request);
        }
    }

    /**
     * 태그 추출을 위한 프롬프트 생성
     */
    private String createTagExtractionPrompt(AiQtRecommendationRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("다음 사용자의 응답을 분석하여 관련된 성경적 키워드/태그를 추출해주세요.\n\n");

        prompt.append("사용자 응답:\n");
        for (String response : request.getUserResponses()) {
            prompt.append("- ").append(response).append("\n");
        }

        if (request.getEmotionalState() != null) {
            prompt.append("\n감정 상태: ").append(request.getEmotionalState()).append("\n");
        }

        prompt.append("\n다음 형식으로 응답해주세요:\n");
        prompt.append("[\"태그1\", \"태그2\", \"태그3\", ...]\n");
        prompt.append("\n예시: [\"위로\", \"소망\", \"기도\", \"인내\", \"사랑\"]\n");

        return prompt.toString();
    }

    /**
     * GPT를 통한 태그 추출
     */
    private String callGptForTagExtraction(String systemPrompt, String userPrompt) {
        // OpenAiClientService의 generateQtSections 메서드를 재사용할 수 없으므로
        // 직접 구현하거나 새로운 메서드를 추가해야 함
        // 여기서는 간단한 구현 예시
        return "[\"위로\", \"소망\", \"기도\"]"; // 임시 응답
    }

    /**
     * AI 응답에서 태그 파싱
     */
    private List<String> parseTagsFromResponse(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            log.warn("Failed to parse tags from response: {}", response);
            return Arrays.asList("위로", "소망", "기도"); // 기본값
        }
    }

    /**
     * 폴백 태그 생성
     */
    private List<String> getFallbackTags(AiQtRecommendationRequest request) {
        List<String> tags = new ArrayList<>();

        // 감정 상태에 따른 기본 태그
        if (request.getEmotionalState() != null) {
            switch (request.getEmotionalState().toLowerCase()) {
                case "sad":
                    tags.addAll(Arrays.asList("위로", "소망", "기쁨"));
                    break;
                case "anxious":
                    tags.addAll(Arrays.asList("평안", "신뢰", "기도"));
                    break;
                case "grateful":
                    tags.addAll(Arrays.asList("감사", "찬양", "기쁨"));
                    break;
                case "confused":
                    tags.addAll(Arrays.asList("지혜", "인도", "신뢰"));
                    break;
                default:
                    tags.addAll(Arrays.asList("사랑", "은혜", "축복"));
            }
        } else {
            tags.addAll(Arrays.asList("사랑", "은혜", "축복"));
        }

        return tags;
    }

    /**
     * AI를 통한 말씀 추천
     */
    private List<RecommendedPassageInfo> getAiRecommendations(
            AiQtRecommendationRequest request, List<String> tags) {

        String prompt = createRecommendationPrompt(request, tags);
        String systemPrompt = "너는 성경 구절을 추천하는 전문가야. 사용자의 상황에 가장 적합한 구절을 추천해줘.";

        // 여기서는 태그 기반으로 DB에서 직접 조회하는 방식으로 구현
        // 실제로는 GPT를 통해 구체적인 구절을 추천받을 수 있음
        List<QtPassage> passages = qtPassageRepository.findByTagsIn(tags);

        return passages.stream()
            .limit(request.getRecommendationCount())
            .map(p -> new RecommendedPassageInfo(
                p.getBook(), p.getChapter(), p.getStartVerse(), p.getEndVerse(),
                calculateRelevanceScore(p, tags)))
            .collect(Collectors.toList());
    }

    /**
     * 추천 프롬프트 생성
     */
    private String createRecommendationPrompt(AiQtRecommendationRequest request, List<String> tags) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("사용자의 상황에 적합한 성경 구절을 추천해주세요.\n\n");

        prompt.append("사용자 상황:\n");
        request.getUserResponses().forEach(r -> prompt.append("- ").append(r).append("\n"));

        prompt.append("\n관련 키워드: ").append(String.join(", ", tags)).append("\n");

        if (request.getPreferredBibleSection() != null) {
            prompt.append("선호 범위: ").append(request.getPreferredBibleSection()).append("\n");
        }

        prompt.append("\n추천 개수: ").append(request.getRecommendationCount()).append("\n");

        return prompt.toString();
    }

    /**
     * 관련성 점수 계산
     */
    private double calculateRelevanceScore(QtPassage passage, List<String> searchTags) {
        if (passage.getTags() == null || passage.getTags().isEmpty()) {
            return 0.0;
        }

        long matchCount = passage.getTags().stream()
            .filter(searchTags::contains)
            .count();

        return (double) matchCount / searchTags.size();
    }

    /**
     * 추천된 구절 정보 조회 및 빌드
     */
    private List<QtRecommendationResponse.RecommendedPassage> findAndBuildRecommendedPassages(
            List<RecommendedPassageInfo> recommendedInfos, Integer limit) {

        List<QtRecommendationResponse.RecommendedPassage> result = new ArrayList<>();

        for (RecommendedPassageInfo info : recommendedInfos) {
            Optional<QtPassage> passageOpt = qtPassageRepository
                .findByBookAndChapterAndStartVerseAndEndVerse(
                    info.book, info.chapter, info.startVerse, info.endVerse);

            if (passageOpt.isPresent()) {
                QtPassage passage = passageOpt.get();
                result.add(buildRecommendedPassage(passage, info.relevanceScore));

                if (result.size() >= limit) {
                    break;
                }
            }
        }

        // 부족한 경우 랜덤 구절 추가
        if (result.size() < limit) {
            List<QtPassage> randomPassages = qtPassageRepository
                .findRandomPassages(limit - result.size());

            for (QtPassage passage : randomPassages) {
                result.add(buildRecommendedPassage(passage, 0.5));
            }
        }

        return result;
    }

    /**
     * 추천 구절 객체 생성
     */
    private QtRecommendationResponse.RecommendedPassage buildRecommendedPassage(
            QtPassage passage, double relevanceScore) {

        return new QtRecommendationResponse.RecommendedPassage(
            passage.getId(),
            passage.getBook(),
            passage.getChapter(),
            passage.getStartVerse(),
            passage.getEndVerse(),
            passage.getTitle(),
            passage.getSummary(),
            passage.getTags(),
            generateBibleText(passage), // 실제 성경 본문은 별도 서비스에서 가져와야 함
            relevanceScore
        );
    }

    /**
     * 성경 본문 생성 (임시)
     */
    private String generateBibleText(QtPassage passage) {
        return String.format("%s %d:%d-%d",
            passage.getBook(), passage.getChapter(),
            passage.getStartVerse(), passage.getEndVerse());
    }

    /**
     * 추천 이유 생성
     */
    private String generateRecommendationReason(
            AiQtRecommendationRequest request, List<String> tags) {

        StringBuilder reason = new StringBuilder();
        reason.append("귀하께서 공유해 주신 내용을 바탕으로 ");

        if (!tags.isEmpty()) {
            reason.append("'").append(String.join("', '", tags.subList(0, Math.min(3, tags.size()))))
                  .append("'와 관련된 ");
        }

        reason.append("말씀을 추천드립니다. ");

        if (request.getEmotionalState() != null) {
            reason.append("현재의 ").append(getEmotionalStateDescription(request.getEmotionalState()))
                  .append(" 상황에서 도움이 되기를 바랍니다.");
        } else {
            reason.append("이 말씀이 큰 위로와 힘이 되기를 바랍니다.");
        }

        return reason.toString();
    }

    /**
     * 감정 상태 설명
     */
    private String getEmotionalStateDescription(String emotionalState) {
        switch (emotionalState.toLowerCase()) {
            case "sad": return "슬픔";
            case "anxious": return "불안함";
            case "grateful": return "감사함";
            case "confused": return "혼란스러움";
            default: return emotionalState;
        }
    }

    /**
     * 추천 구절 정보 내부 클래스
     */
    private static class RecommendedPassageInfo {
        String book;
        Integer chapter;
        Integer startVerse;
        Integer endVerse;
        Double relevanceScore;

        RecommendedPassageInfo(String book, Integer chapter, Integer startVerse,
                               Integer endVerse, Double relevanceScore) {
            this.book = book;
            this.chapter = chapter;
            this.startVerse = startVerse;
            this.endVerse = endVerse;
            this.relevanceScore = relevanceScore;
        }
    }
}