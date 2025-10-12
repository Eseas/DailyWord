package com.dailyword.qt.application.usecase;

import com.dailyword.qt.domain.model.entity.UserQtHistory;

public interface RecordQtHistoryUsecase {
    /**
     * 사용자의 QT 읽기 기록 저장
     *
     * @param userId 사용자 ID
     * @param passageId QT 구절 ID
     * @param userNote 사용자 메모 (선택사항)
     * @return 저장된 QT 히스토리
     */
    UserQtHistory recordQtReading(Long userId, Long passageId, String userNote);

    /**
     * QT 완료 표시
     *
     * @param historyId 히스토리 ID
     */
    void markAsCompleted(Long historyId);
}