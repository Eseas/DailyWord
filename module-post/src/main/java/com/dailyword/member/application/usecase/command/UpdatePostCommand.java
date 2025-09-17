package com.dailyword.member.application.usecase.command;

import lombok.Getter;

@Getter
public class UpdatePostCommand {
    private final String refCode;
    private final Long memberId;
    private final String content;
    private final Boolean isHide;

    public UpdatePostCommand(String refCode, Long memberId, String content, Boolean isHide) {
        this.refCode = refCode;
        this.memberId = memberId;
        this.content = content;
        this.isHide = isHide;
    }
}
