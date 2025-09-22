package com.dailyword.gateway.adapter.out.client;

import com.dailyword.common.response.APIResponse;
import com.dailyword.gateway.dto.file.FileInfoResponse;
import com.dailyword.gateway.dto.file.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "fileClient", url = "${module.file.url}")
public interface FileClient {

    @PostMapping(value = "/internal/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    APIResponse<FileUploadResponse> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("uploaderId") Long uploaderId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic
    );

    @GetMapping("/internal/files/{fileRefCode}")
    APIResponse<FileInfoResponse> getFileInfo(@PathVariable String fileRefCode);

    @GetMapping("/internal/files/{fileRefCode}/download-url")
    APIResponse<String> generateDownloadUrl(
            @PathVariable String fileRefCode,
            @RequestParam("requesterId") Long requesterId
    );

    @DeleteMapping("/internal/files/{fileRefCode}")
    APIResponse<Void> deleteFile(
            @PathVariable String fileRefCode,
            @RequestParam("requesterId") Long requesterId
    );
}