package com.dailyword.gateway.common.config;

import com.dailyword.common.exception.BusinessException;
import com.dailyword.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        log.error("FeignClient error: methodKey={}, status={}", methodKey, status);

        try (InputStream bodyStream = response.body().asInputStream()) {
            ErrorResponse errorResponse = objectMapper.readValue(bodyStream, ErrorResponse.class);

            if (errorResponse.isBusinessException()) {
                return new BusinessException(errorResponse.getCode(), errorResponse.getMessage());
            }

            return new RuntimeException(errorResponse.getMessage());
        } catch (IOException e) {
            log.error("Failed to parse error response: {}", e.getMessage());
            return new RuntimeException("Feign 호출 실패: " + methodKey + ", status=" + status);
        }
    }
}
