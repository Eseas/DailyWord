package com.dailyword.gateway.application.service.post.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateCommand {
    private Long authorId;
    private String content;
    private List<String> hashTas;
}
