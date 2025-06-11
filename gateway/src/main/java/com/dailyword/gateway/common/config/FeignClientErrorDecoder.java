package com.dailyword.gateway.common.config;

import com.dailyword.gateway.exception.AuthApiException;
import com.dailyword.gateway.exception.KakaoApiException;
import com.dailyword.gateway.exception.MemberApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        log.error("FeignClient error: methodKey={}, status={}", methodKey, status);

        if (methodKey.contains("MemberClient")) {
            return new MemberApiException("Member API 호출 실패", status);
        }

        if (methodKey.contains("AuthClient")) {
            return new AuthApiException("Auth API 호출 실패", status);
        }

        if (methodKey.contains("KakaoClient")) {
            return new KakaoApiException("Kakao API 호출 실패", status);
        }

        return new RuntimeException("Feign 호출 실패: " + methodKey + ", status=" + status);
    }
}
