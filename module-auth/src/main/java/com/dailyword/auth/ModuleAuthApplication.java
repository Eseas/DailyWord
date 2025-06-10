package com.dailyword.auth;

import com.dailyword.auth.config.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AuthProperties.class)
public class ModuleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleAuthApplication.class, args);
    }

}
