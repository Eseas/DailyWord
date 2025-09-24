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

/**
 * 파일 관리 Controller
 * 외부 클라이언트의 파일 업로드, 다운로드, 삭제 요청을 처리하는 Gateway API 컨트롤러입니다.
 * module-file과 연동하여 파일 관리 기능을 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class FileController {

    private final FileUsecase fileUsecase;

    /**
     * 파일 업로드
     * MultipartFile을 받아 파일을 업로드하고 파일 정보를 반환합니다.
     * 내부적으로 module-file을 통해 S3에 업로드됩니다.
     *
     * @param file 업로드할 파일 (MultipartFile)
     * @param uploaderId 파일을 업로드하는 사용자의 ID
     * @param description 파일에 대한 설명 (선택사항)
     * @param isPublic 파일 공개 여부 (기본값: false)
     * @return 업로드된 파일의 정보 (fileRefCode, 파일명, URL 등)
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
     * 파일 참조 코드를 통해 파일의 상세 정보를 조회합니다.
     *
     * @param fileRefCode 조회할 파일의 참조 코드
     * @return 파일의 상세 정보 (파일명, 크기, 타입, 생성일시 등)
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
     * 파일에 접근할 수 있는 Presigned URL을 생성합니다.
     * 권한 검증 후 1시간 동안 유효한 다운로드 링크를 제공합니다.
     *
     * @param fileRefCode 다운로드할 파일의 참조 코드
     * @param requesterId 다운로드를 요청하는 사용자의 ID
     * @return S3 Presigned URL (1시간 동안 유효한 다운로드 링크)
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
     * 파일을 삭제합니다. 파일 업로더만 삭제할 수 있습니다.
     * S3에서 실제 파일을 삭제하고 데이터베이스 상태를 변경합니다.
     *
     * @param fileRefCode 삭제할 파일의 참조 코드
     * @param requesterId 삭제를 요청하는 사용자의 ID (업로더와 일치해야 함)
     * @return 삭제 완료 응답
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