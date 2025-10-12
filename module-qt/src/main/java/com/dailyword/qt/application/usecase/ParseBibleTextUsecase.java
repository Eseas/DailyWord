package com.dailyword.qt.application.usecase;

import com.dailyword.qt.dto.BibleVerse;
import com.dailyword.qt.dto.QtSectionDto;

import java.util.List;

public interface ParseBibleTextUsecase {
    List<QtSectionDto> parseBibleText(String book, int chapter, List<BibleVerse> verses);
}