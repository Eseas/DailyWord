package com.dailyword.notification.adapter.in.facade.dto;

import com.dailyword.notification.application.usecase.command.SlackMessageCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackMessageRequest {
    private String message;
    private String channel;

    public SlackMessageCommand toCommand() {
        return new SlackMessageCommand(message, channel);
    }
}
