package com.dailyword.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(FileProperties.class)
public class ModuleFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleFileApplication.class, args);
    }

}
