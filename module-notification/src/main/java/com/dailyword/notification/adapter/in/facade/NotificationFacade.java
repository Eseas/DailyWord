package com.dailyword.notification.adapter.in.facade;

import com.dailyword.notification.adapter.in.facade.dto.SlackMessageRequest;
import com.dailyword.notification.application.usecase.SendSlackMessageUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class NotificationFacade {

    private final SendSlackMessageUsecase sendSlackMessageUsecase;

    @PostMapping("/slack/message")
    public ResponseEntity<Void> sendSlackMessage(
            @RequestBody SlackMessageRequest request
    ) {
        sendSlackMessageUsecase.sendMessage(request.toCommand());
        return ResponseEntity.ok().build();
    }
}
