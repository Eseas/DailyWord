package com.dailyword.member.application.usecase.command;

import lombok.Getter;

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
