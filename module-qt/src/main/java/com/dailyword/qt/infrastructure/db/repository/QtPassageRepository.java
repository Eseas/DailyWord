package com.dailyword.qt.infrastructure.db.repository;

import com.dailyword.qt.domain.model.entity.QtPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QtPassageRepository extends JpaRepository<QtPassage, Long> {
}
