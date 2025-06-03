package com.dailyword.qt.dto;

import com.dailyword.qt.entity.QtPassage;
import lombok.Getter;

import java.util.List;

@Getter
public class QtSectionDto {
    private String group_id;
    private String group_title;
    private int group_order;
    private int start_verse;
    private int end_verse;
    private String title;
    private String summary;
    private List<String> tags;

    public QtPassage toEntity(String book, int chapter) {
        return new QtPassage(
                book,
                chapter,
                start_verse,
                end_verse,
                title,
                summary,
                tags,
                group_id,
                group_title,
                group_order
        );
    }
}