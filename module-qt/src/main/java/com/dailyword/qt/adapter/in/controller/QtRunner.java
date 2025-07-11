package com.dailyword.qt.adapter.in.controller;

import com.dailyword.qt.application.service.QtGenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class QtRunner implements CommandLineRunner {
    private final QtGenerateService qtGenerateService;

    @Override
    public void run(String... args) throws Exception {
        qtGenerateService.processBibleCsv(Path.of("bible.csv"));
    }
}