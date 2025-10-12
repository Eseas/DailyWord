package com.dailyword.qt.application.usecase;

import com.dailyword.qt.dto.QtSectionDto;

import java.util.List;

public interface GenerateQtContentUsecase {
    void generateAndSaveQtContent(String book, Integer chapter, List<QtSectionDto> sections);
}