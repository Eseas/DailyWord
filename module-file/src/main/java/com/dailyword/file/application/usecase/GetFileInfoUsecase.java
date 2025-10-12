package com.dailyword.file.application.usecase;

import com.dailyword.file.adapter.in.facade.dto.FileInfoResponse;

public interface GetFileInfoUsecase {
    FileInfoResponse getFileInfo(String fileRefCode);
}