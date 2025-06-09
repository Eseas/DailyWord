package com.dailyword.gateway.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.kakao.KakaoUserInfoResponse;
import com.dailyword.gateway.exception.KakaoApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoClient {
    private final WebClient kakaoWebClient;

    public KakaoUserInfoResponse getUserInfo(String kakaoCode) {
        APIResponse<KakaoUserInfoResponse> kakaoResponse = kakaoWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/internal/kakao/login")
                        .queryParam("code", kakaoCode)
                        .build())
                .retrieve()
                .onStatus(status -> status.isError(), r ->
                        r.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> {
                                    log.error("Kakao API error: status={}, body={}", r.statusCode(), body);
                                    return Mono.error(new KakaoApiException("Kakao API 호출 실패", r.statusCode().value()));
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<APIResponse<KakaoUserInfoResponse>>() {})
                .block();

        if (kakaoResponse == null || !kakaoResponse.isSuccess()) {
            throw new KakaoApiException("카카오 유저 정보 조회 실패", 400);
        }

        return kakaoResponse.getData();
    }
}
