package com.dailyword.notification.adapter.in.facade.dto;

import com.dailyword.notification.application.usecase.command.SlackMessageCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackMessageRequest {
    @NotBlank(message = "메시지는 필수입니다")
    private String message;
    private String channel;

    public SlackMessageCommand toCommand() {
        return new SlackMessageCommand(message, channel);
    }
}
