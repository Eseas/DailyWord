package com.dailyword.file.repository;

import com.dailyword.file.domain.model.File;
import com.dailyword.file.domain.model.FileStatus;
import com.dailyword.file.domain.model.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByRefCode(String refCode);

    Optional<File> findByStoredFileName(String storedFileName);

    Page<File> findByUploaderIdAndStatus(Long uploaderId, FileStatus status, Pageable pageable);

    Page<File> findByUploaderIdAndFileTypeAndStatus(Long uploaderId, FileType fileType, FileStatus status, Pageable pageable);

    List<File> findByStatusAndIsPublic(FileStatus status, Boolean isPublic);

    @Query("SELECT f FROM File f WHERE f.status = :status AND f.expiresAt IS NOT NULL AND f.expiresAt < :now")
    List<File> findExpiredFiles(@Param("status") FileStatus status, @Param("now") LocalDateTime now);

    @Query("SELECT f FROM File f WHERE f.uploaderId = :uploaderId AND f.status = :status ORDER BY f.createdAt DESC")
    List<File> findRecentFilesByUploader(@Param("uploaderId") Long uploaderId, @Param("status") FileStatus status);

    long countByUploaderIdAndStatus(Long uploaderId, FileStatus status);

    @Query("SELECT SUM(f.fileSize) FROM File f WHERE f.uploaderId = :uploaderId AND f.status = :status")
    Long getTotalFileSizeByUploader(@Param("uploaderId") Long uploaderId, @Param("status") FileStatus status);
}