package com.dailyword.common.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
public class SlackNotificationService {

    private static final String SLACK_API_URL = "https://slack.com/api/chat.postMessage";

    private final WebClient webClient;
    private final String errorChannel;

    public SlackNotificationService(
            @Value("${slack.bot-token:}") String botToken,
            @Value("${slack.error-channel:}") String errorChannel
    ) {
        this.errorChannel = errorChannel;
        this.webClient = WebClient.builder()
                .baseUrl(SLACK_API_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + botToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Async
    public void sendErrorNotification(String message) {
        if (errorChannel == null || errorChannel.isBlank()) {
            log.debug("Slack error channel is not configured. Skipping notification.");
            return;
        }

        try {
            Map<String, String> payload = Map.of(
                    "channel", errorChannel,
                    "text", message
            );

            webClient.post()
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> log.info("Slack error notification sent successfully"))
                    .doOnError(error -> log.error("Failed to send Slack error notification: {}", error.getMessage()))
                    .subscribe();
        } catch (Exception e) {
            log.error("Failed to send Slack error notification: {}", e.getMessage());
        }
    }
}
