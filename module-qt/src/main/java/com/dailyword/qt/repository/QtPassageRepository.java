package com.dailyword.qt.repository;

import com.dailyword.qt.entity.QtPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QtPassageRepository extends JpaRepository<QtPassage, Long> {
}
