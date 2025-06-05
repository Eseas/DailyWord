package com.dailyword.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient memberWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    @Bean
    public WebClient wordWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8081").build();
    }

    @Bean
    public WebClient kakaoWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8082").build();
    }
}
