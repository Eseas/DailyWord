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

/**
 * 파일 관리 Facade
 * 파일 업로드, 다운로드, 삭제 등의 내부 API를 제공합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class FileFacade {

    private final FileUploadUsecase fileUploadUsecase;

    /**
     * 파일 업로드
     * MultipartFile을 받아 S3에 업로드하고 파일 메타데이터를 데이터베이스에 저장합니다.
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
        FileUploadResponse response = fileUploadUsecase.uploadFile(file, uploaderId, description, isPublic);
        return ResponseEntity.ok(APIResponse.success(response));
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
        FileInfoResponse response = fileUploadUsecase.getFileInfo(fileRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    /**
     * 파일 다운로드 URL 생성
     * 파일에 접근할 수 있는 Presigned URL을 생성합니다. (1시간 유효)
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
        String downloadUrl = fileUploadUsecase.generateDownloadUrl(fileRefCode, requesterId);
        return ResponseEntity.ok(APIResponse.success(downloadUrl));
    }

    /**
     * 파일 삭제
     * S3에서 파일을 삭제하고 데이터베이스의 파일 상태를 DELETED로 변경합니다.
     * 파일 업로더만 삭제할 수 있습니다.
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
        fileUploadUsecase.deleteFile(fileRefCode, requesterId);
        return ResponseEntity.ok(APIResponse.success());
    }
}