package com.dailyword.auth;

import com.dailyword.auth.config.KakaoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KakaoProperties.class)
public class ModuleKakaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleKakaoApplication.class, args);
    }

}
