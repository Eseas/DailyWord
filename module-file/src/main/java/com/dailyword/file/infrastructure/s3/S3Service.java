package com.dailyword.file.infrastructure.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public S3UploadResult uploadFile(MultipartFile file, String directory) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);
            String storedFileName = generateStoredFileName(directory, fileExtension);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storedFileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
                    bucketName, region, storedFileName);

            log.info("File uploaded successfully: {}", storedFileName);

            return S3UploadResult.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .fileUrl(fileUrl)
                    .fileSize(file.getSize())
                    .mimeType(file.getContentType())
                    .bucketName(bucketName)
                    .build();

        } catch (IOException e) {
            log.error("Failed to upload file to S3", e);
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    public String generatePresignedDownloadUrl(String storedFileName, Duration expiration) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storedFileName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(expiration)
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url().toString();

        } catch (Exception e) {
            log.error("Failed to generate presigned URL for file: {}", storedFileName, e);
            throw new RuntimeException("다운로드 URL 생성에 실패했습니다.", e);
        }
    }

    public void deleteFile(String storedFileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storedFileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("File deleted successfully: {}", storedFileName);

        } catch (Exception e) {
            log.error("Failed to delete file from S3: {}", storedFileName, e);
            throw new RuntimeException("파일 삭제에 실패했습니다.", e);
        }
    }

    public boolean fileExists(String storedFileName) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storedFileName)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;

        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("Failed to check file existence: {}", storedFileName, e);
            return false;
        }
    }

    private String generateStoredFileName(String directory, String fileExtension) {
        String uuid = UUID.randomUUID().toString();
        return String.format("%s/%s%s", directory, uuid, fileExtension);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}