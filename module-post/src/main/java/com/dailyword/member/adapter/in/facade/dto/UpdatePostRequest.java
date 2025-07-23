package com.dailyword.member.adapter.in.facade.dto;

import com.dailyword.member.application.usecase.command.UpdatePostCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePostRequest {
    private Long memberId;
    private String content;

    public UpdatePostCommand toCommand(String refCode) {
        return new UpdatePostCommand(refCode, memberId, content);
    }
}
