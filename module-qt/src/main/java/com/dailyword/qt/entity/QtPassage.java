package com.dailyword.qt.entity;

import com.dailyword.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@EntityScan
@Getter
@NoArgsConstructor
@Table(name = "qt_passage")
public class QtPassage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String book;
    private int chapter;
    private int startVerse;
    private int endVerse;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ElementCollection
    private List<String> tags;

    private String groupId;
    private String groupTitle;
    private int groupOrder;

    public QtPassage(
            String book, int chapter, int startVerse, int endVerse,
            String title, String summary, List<String> tags,
            String groupId, String groupTitle, int groupOrder
    ) {
        this.book = book;
        this.chapter = chapter;
        this.startVerse = startVerse;
        this.endVerse = endVerse;
        this.title = title;
        this.summary = summary;
        this.tags = tags;
        this.groupId = groupId;
        this.groupTitle = groupTitle;
        this.groupOrder = groupOrder;
    }
}