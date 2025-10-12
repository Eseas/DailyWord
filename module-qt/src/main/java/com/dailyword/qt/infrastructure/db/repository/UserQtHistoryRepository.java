package com.dailyword.qt.infrastructure.db.repository;

import com.dailyword.qt.domain.model.entity.UserQtHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQtHistoryRepository extends JpaRepository<UserQtHistory, Long> {

    /**
     * 사용자의 가장 최근 QT 기록 조회
     */
    Optional<UserQtHistory> findTopByUserIdOrderByReadAtDesc(Long userId);

    /**
     * 사용자의 QT 기록 목록 조회 (최신순)
     */
    List<UserQtHistory> findByUserIdOrderByReadAtDesc(Long userId);

    /**
     * 사용자가 특정 구절을 읽었는지 확인
     */
    boolean existsByUserIdAndQtPassageId(Long userId, Long qtPassageId);

    /**
     * 사용자가 읽은 QT 구절 ID 목록 조회
     */
    @Query("SELECT h.qtPassage.id FROM UserQtHistory h WHERE h.userId = :userId")
    List<Long> findReadPassageIdsByUserId(@Param("userId") Long userId);

    /**
     * 사용자의 완료된 QT 개수 조회
     */
    long countByUserIdAndIsCompleted(Long userId, Boolean isCompleted);
}