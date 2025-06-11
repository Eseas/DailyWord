package com.dailyword.gateway.common.config;


import com.dailyword.gateway.common.logging.TraceIdRestTemplateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final TraceIdRestTemplateInterceptor traceIdRestTemplateInterceptor;

    public RestTemplateConfig(TraceIdRestTemplateInterceptor traceIdRestTemplateInterceptor) {
        this.traceIdRestTemplateInterceptor = traceIdRestTemplateInterceptor;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(traceIdRestTemplateInterceptor);
        return restTemplate;
    }
}
