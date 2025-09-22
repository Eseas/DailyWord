package com.dailyword.gateway.adapter.in.controller;

import com.dailyword.common.response.APIResponse;
import com.dailyword.common.response.SuccessCode;
import com.dailyword.gateway.application.usecase.file.FileUsecase;
import com.dailyword.gateway.dto.file.FileInfoResponse;
import com.dailyword.gateway.dto.file.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class FileController {

    private final FileUsecase fileUsecase;

    /**
     * 파일 업로드
     * @param file 업로드할 파일
     * @param uploaderId 업로드하는 사용자 ID
     * @param description 파일 설명 (선택)
     * @param isPublic 공개 여부 (기본값: false)
     * @return 업로드된 파일 정보
     */
    @PostMapping("/files/upload")
    public ResponseEntity<APIResponse<FileUploadResponse>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploaderId") Long uploaderId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic
    ) {
        FileUploadResponse response = fileUsecase.uploadFile(file, uploaderId, description, isPublic);
        return ResponseEntity.ok(APIResponse.success(SuccessCode.SUCCESS, response));
    }

    /**
     * 파일 정보 조회
     * @param fileRefCode 파일 참조 코드
     * @return 파일 정보
     */
    @GetMapping("/files/{fileRefCode}")
    public ResponseEntity<APIResponse<FileInfoResponse>> getFileInfo(
            @PathVariable String fileRefCode
    ) {
        FileInfoResponse response = fileUsecase.getFileInfo(fileRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    /**
     * 파일 다운로드 URL 생성
     * @param fileRefCode 파일 참조 코드
     * @param requesterId 요청하는 사용자 ID
     * @return 다운로드 URL
     */
    @GetMapping("/files/{fileRefCode}/download-url")
    public ResponseEntity<APIResponse<String>> generateDownloadUrl(
            @PathVariable String fileRefCode,
            @RequestParam("requesterId") Long requesterId
    ) {
        String downloadUrl = fileUsecase.generateDownloadUrl(fileRefCode, requesterId);
        return ResponseEntity.ok(APIResponse.success(downloadUrl));
    }

    /**
     * 파일 삭제
     * @param fileRefCode 파일 참조 코드
     * @param requesterId 요청하는 사용자 ID
     */
    @DeleteMapping("/files/{fileRefCode}")
    public ResponseEntity<APIResponse<Void>> deleteFile(
            @PathVariable String fileRefCode,
            @RequestParam("requesterId") Long requesterId
    ) {
        fileUsecase.deleteFile(fileRefCode, requesterId);
        return ResponseEntity.ok(APIResponse.success());
    }
}