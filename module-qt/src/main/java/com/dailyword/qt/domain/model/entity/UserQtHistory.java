package com.dailyword.qt.domain.model.entity;

import com.dailyword.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 QT 히스토리 엔티티
 * 사용자가 읽은 QT 말씀 기록을 저장
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_qt_history")
public class UserQtHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qt_passage_id", nullable = false)
    private QtPassage qtPassage;

    @Column(name = "read_at", nullable = false)
    private LocalDateTime readAt;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(columnDefinition = "TEXT")
    private String userNote;

    public UserQtHistory(Long userId, QtPassage qtPassage, LocalDateTime readAt,
                         Boolean isCompleted, String userNote) {
        this.userId = userId;
        this.qtPassage = qtPassage;
        this.readAt = readAt != null ? readAt : LocalDateTime.now();
        this.isCompleted = isCompleted != null ? isCompleted : false;
        this.userNote = userNote;
    }

    public UserQtHistory(Long userId, QtPassage qtPassage) {
        this(userId, qtPassage, LocalDateTime.now(), false, null);
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void updateUserNote(String userNote) {
        this.userNote = userNote;
    }
}