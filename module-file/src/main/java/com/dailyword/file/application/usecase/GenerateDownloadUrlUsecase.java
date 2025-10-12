package com.dailyword.file.application.usecase;

public interface GenerateDownloadUrlUsecase {
    String generateDownloadUrl(String fileRefCode, Long requesterId);
}