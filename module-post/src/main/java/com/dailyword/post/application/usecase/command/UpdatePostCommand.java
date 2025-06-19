package com.dailyword.post.application.usecase.command;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UpdatePostCommand {
    private final String refCode;
    private final Long memberId;
    private final String content;

    public UpdatePostCommand(String refCode, Long memberId, String content) {
        this.refCode = refCode;
        this.memberId = memberId;
        this.content = content;
    }
}
