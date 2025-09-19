package com.dailyword.post.application.usecase.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteCommentCommand {
    private final String postRefCode;
    private final Long commentId;
    private final Long memberId;
}
