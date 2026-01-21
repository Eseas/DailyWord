package com.dailyword.notification.adapter.out.slack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SlackClient {

    private static final String SLACK_API_URL = "https://slack.com/api/chat.postMessage";

    private final WebClient webClient;
    private final String defaultChannel;

    public SlackClient(
            @Value("${slack.bot-token}") String botToken,
            @Value("${slack.default-channel}") String defaultChannel
    ) {
        this.defaultChannel = defaultChannel;
        this.webClient = WebClient.builder()
                .baseUrl(SLACK_API_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + botToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void sendMessage(String message) {
        sendMessage(defaultChannel, message);
    }

    public void sendMessage(String channel, String message) {
        SlackApiResponse response = doSendMessage(channel, message);
        validateResponse(response);
    }

    @Async
    public CompletableFuture<Void> sendMessageAsync(String message) {
        return sendMessageAsync(defaultChannel, message);
    }

    @Async
    public CompletableFuture<Void> sendMessageAsync(String channel, String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                SlackApiResponse response = doSendMessage(channel, message);
                validateResponse(response);
            } catch (Exception e) {
                log.error("Failed to send Slack message asynchronously: {}", e.getMessage());
            }
        });
    }

    private SlackApiResponse doSendMessage(String channel, String message) {
        Map<String, String> payload = Map.of(
                "channel", channel,
                "text", message
        );

        try {
            SlackApiResponse response = webClient.post()
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(SlackApiResponse.class)
                    .block();

            log.info("Slack API response - ok: {}, channel: {}",
                    response != null && response.isOk(), channel);
            return response;
        } catch (WebClientResponseException e) {
            log.error("Slack API HTTP error - status: {}, body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw new SlackApiException("Slack API 호출 실패: " + e.getStatusCode(), e);
        } catch (Exception e) {
            log.error("Slack API call failed: {}", e.getMessage());
            throw new SlackApiException("Slack API 호출 중 오류 발생", e);
        }
    }

    private void validateResponse(SlackApiResponse response) {
        if (response == null) {
            throw new SlackApiException("EMPTY_RESPONSE", "Slack API 응답이 비어있습니다");
        }
        if (response.isFailed()) {
            log.error("Slack API returned error: {}", response.getError());
            throw new SlackApiException(response.getError(),
                    "Slack 메시지 전송 실패: " + response.getError());
        }
        log.info("Slack message sent successfully to channel, ts: {}", response.getTs());
    }
}
