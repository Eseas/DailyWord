package com.dailyword.post.adapter.in.facade.dto;

import com.dailyword.post.application.usecase.command.UpdatePostCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePostRequest {
    private Long memberId;
    private String content;
    private Boolean isHide;

    public UpdatePostCommand toCommand(String refCode) {
        return new UpdatePostCommand(refCode, memberId, content, isHide);
    }
}
