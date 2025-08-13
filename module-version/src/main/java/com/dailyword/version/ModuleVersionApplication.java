package com.dailyword.version;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(FileProperties.class)
public class ModuleVersionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleVersionApplication.class, args);
    }

}
