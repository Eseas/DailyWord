package com.dailyword.qt.infrastructure.db.repository;

import com.dailyword.qt.domain.model.entity.QtPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QtPassageRepository extends JpaRepository<QtPassage, Long> {

    /**
     * 특정 책과 장의 구절 조회 (순서대로)
     */
    List<QtPassage> findByBookAndChapterOrderByStartVerse(String book, Integer chapter);

    /**
     * 특정 책, 장, 절 범위로 구절 조회
     */
    Optional<QtPassage> findByBookAndChapterAndStartVerseAndEndVerse(
        String book, Integer chapter, Integer startVerse, Integer endVerse);

    /**
     * 다음 구절 조회 (같은 장 내에서)
     */
    @Query("SELECT p FROM QtPassage p WHERE p.book = :book AND p.chapter = :chapter " +
           "AND p.startVerse > :currentEndVerse ORDER BY p.startVerse ASC")
    List<QtPassage> findNextPassagesInChapter(@Param("book") String book,
                                              @Param("chapter") Integer chapter,
                                              @Param("currentEndVerse") Integer currentEndVerse);

    /**
     * 태그로 구절 검색
     */
    @Query("SELECT p FROM QtPassage p WHERE :tag MEMBER OF p.tags")
    List<QtPassage> findByTag(@Param("tag") String tag);

    /**
     * 여러 태그로 구절 검색 (OR 조건)
     */
    @Query("SELECT DISTINCT p FROM QtPassage p WHERE EXISTS " +
           "(SELECT t FROM p.tags t WHERE t IN :tags)")
    List<QtPassage> findByTagsIn(@Param("tags") List<String> tags);

    /**
     * 책 목록 조회
     */
    @Query("SELECT DISTINCT p.book FROM QtPassage p ORDER BY p.book")
    List<String> findDistinctBooks();

    /**
     * 특정 책의 장 목록 조회
     */
    @Query("SELECT DISTINCT p.chapter FROM QtPassage p WHERE p.book = :book ORDER BY p.chapter")
    List<Integer> findDistinctChaptersByBook(@Param("book") String book);

    /**
     * 랜덤 구절 조회
     */
    @Query(value = "SELECT * FROM qt_passage ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<QtPassage> findRandomPassages(@Param("count") Integer count);
}
