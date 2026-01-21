package com.dailyword.notification.application.usecase.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SlackMessageCommand {
    private final String message;
    private final String channel;
}
