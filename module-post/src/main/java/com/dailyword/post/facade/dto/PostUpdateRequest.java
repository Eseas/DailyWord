package com.dailyword.post.facade.dto;

import com.dailyword.post.application.usecase.command.UpdatePostCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {
    private Long memberId;
    private String content;

    public UpdatePostCommand toCommand(String refCode) {
        return new UpdatePostCommand(refCode, memberId, content);
    }
}
