package com.dailyword.file.application.usecase;

public interface DeleteFileUsecase {
    void deleteFile(String fileRefCode, Long requesterId);
}