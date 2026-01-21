package com.dailyword.notification.application.service;

import com.dailyword.notification.adapter.out.slack.SlackClient;
import com.dailyword.notification.application.usecase.SendSlackMessageUsecase;
import com.dailyword.notification.application.usecase.command.SlackMessageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackMessageService implements SendSlackMessageUsecase {

    private final SlackClient slackClient;

    @Override
    public void sendMessage(SlackMessageCommand command) {
        if (command.getChannel() != null && !command.getChannel().isBlank()) {
            slackClient.sendMessage(command.getChannel(), command.getMessage());
        } else {
            slackClient.sendMessage(command.getMessage());
        }
    }
}
