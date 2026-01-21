package com.dailyword.notification.adapter.out.slack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

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
        Map<String, String> payload = Map.of(
                "channel", channel,
                "text", message
        );

        webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Slack message sent successfully: {}", response))
                .doOnError(error -> log.error("Failed to send Slack message: {}", error.getMessage()))
                .block();
    }
}
