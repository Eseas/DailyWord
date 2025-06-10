package com.dailyword.gateway.client;

import com.dailyword.gateway.dto.auth.RefreshTokenRequest;
import com.dailyword.gateway.dto.auth.TokenRequest;
import com.dailyword.gateway.dto.auth.TokenResponse;
import com.dailyword.gateway.exception.AuthApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthClient {

    private final WebClient authWebClient;

    public TokenResponse generateToken(TokenRequest request) {
        return authWebClient.post()
                .uri("/internal/auth/token")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.isError(), r ->
                        r.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> {
                                    log.error("Auth API token generate error: status={}, body={}", r.statusCode(), body);
                                    return Mono.error(new AuthApiException("Auth API token 발급 실패", r.statusCode().value()));
                                })
                )
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return authWebClient.post()
                .uri("/internal/auth/refresh")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.isError(), r ->
                        r.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> {
                                    log.error("Auth API refresh token error: status={}, body={}", r.statusCode(), body);
                                    return Mono.error(new AuthApiException("Auth API refresh token 실패", r.statusCode().value()));
                                })
                )
                .bodyToMono(TokenResponse.class)
                .block();
    }
}