package com.dailyword.notification.application.usecase;

import com.dailyword.notification.application.usecase.command.SlackMessageCommand;

public interface SendSlackMessageUsecase {
    void sendMessage(SlackMessageCommand command);
}
