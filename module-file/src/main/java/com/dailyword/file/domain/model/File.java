package com.dailyword.file.domain.model;

import com.dailyword.common.domain.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@NoArgsConstructor
@Getter
public class File extends BaseUuidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String storedFileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private String s3Url;

    @Column(nullable = false)
    private String s3Bucket;

    @Column(nullable = false)
    private Long uploaderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileStatus status = FileStatus.PENDING;

    private String description;

    @Column(nullable = false)
    private Boolean isPublic = false;

    private LocalDateTime expiresAt;

    private File(String originalFileName, String storedFileName, String fileExtension,
                Long fileSize, String mimeType, String s3Url, String s3Bucket,
                Long uploaderId, FileType fileType, String description, Boolean isPublic) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.s3Url = s3Url;
        this.s3Bucket = s3Bucket;
        this.uploaderId = uploaderId;
        this.fileType = fileType;
        this.description = description;
        this.isPublic = isPublic;
        this.status = FileStatus.PENDING;
    }

    public static File create(String originalFileName, String storedFileName, String fileExtension,
                             Long fileSize, String mimeType, String s3Url, String s3Bucket,
                             Long uploaderId, String description, Boolean isPublic) {
        FileType fileType = FileType.fromMimeType(mimeType);
        return new File(originalFileName, storedFileName, fileExtension, fileSize, mimeType,
                       s3Url, s3Bucket, uploaderId, fileType, description, isPublic);
    }

    public void activate() {
        this.status = FileStatus.ACTIVE;
    }

    public void delete() {
        this.status = FileStatus.DELETED;
    }

    public void markAsCorrupted() {
        this.status = FileStatus.CORRUPTED;
    }

    public void setExpiration(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateVisibility(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}