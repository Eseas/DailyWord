package com.dailyword.member.application.usecase.command;

public class DeletePostCommand {
    private final String refCode;
    private final Long memberId;

    public DeletePostCommand(String refCode, Long memberId) {
        this.refCode = refCode;
        this.memberId = memberId;
    }

    public String getRefCode() {
        return refCode;
    }

    public Long getMemberId() {
        return memberId;
    }
}
