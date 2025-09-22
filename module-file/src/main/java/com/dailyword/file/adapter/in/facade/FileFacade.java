package com.dailyword.file.adapter.in.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.file.adapter.in.facade.dto.FileInfoResponse;
import com.dailyword.file.adapter.in.facade.dto.FileUploadResponse;
import com.dailyword.file.application.usecase.FileUploadUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class FileFacade {

    private final FileUploadUsecase fileUploadUsecase;

    /**
     * 파일 업로드
     */
    @PostMapping("/files/upload")
    public ResponseEntity<APIResponse<FileUploadResponse>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploaderId") Long uploaderId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic
    ) {
        FileUploadResponse response = fileUploadUsecase.uploadFile(file, uploaderId, description, isPublic);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    /**
     * 파일 정보 조회
     */
    @GetMapping("/files/{fileRefCode}")
    public ResponseEntity<APIResponse<FileInfoResponse>> getFileInfo(
            @PathVariable String fileRefCode
    ) {
        FileInfoResponse response = fileUploadUsecase.getFileInfo(fileRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    /**
     * 파일 다운로드 URL 생성
     */
    @GetMapping("/files/{fileRefCode}/download-url")
    public ResponseEntity<APIResponse<String>> generateDownloadUrl(
            @PathVariable String fileRefCode,
            @RequestParam("requesterId") Long requesterId
    ) {
        String downloadUrl = fileUploadUsecase.generateDownloadUrl(fileRefCode, requesterId);
        return ResponseEntity.ok(APIResponse.success(downloadUrl));
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping("/files/{fileRefCode}")
    public ResponseEntity<APIResponse<Void>> deleteFile(
            @PathVariable String fileRefCode,
            @RequestParam("requesterId") Long requesterId
    ) {
        fileUploadUsecase.deleteFile(fileRefCode, requesterId);
        return ResponseEntity.ok(APIResponse.success());
    }
}