package com.dailyword.post.facade.dto;

import com.dailyword.post.application.usecase.command.DeletePostCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeletePostRequest {
    private Long memberId;

    public DeletePostCommand toCommand(String refCode) {
        return new DeletePostCommand(refCode, memberId);
    }
}
