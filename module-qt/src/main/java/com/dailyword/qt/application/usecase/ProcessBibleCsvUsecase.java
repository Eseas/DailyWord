package com.dailyword.qt.application.usecase;

import java.nio.file.Path;

public interface ProcessBibleCsvUsecase {
    void processBibleCsv(Path csvPath) throws Exception;
}