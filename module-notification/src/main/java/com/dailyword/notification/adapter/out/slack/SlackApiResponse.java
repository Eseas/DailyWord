package com.dailyword.notification.adapter.out.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackApiResponse {
    private boolean ok;
    private String error;
    private String channel;
    private String ts;

    public boolean isFailed() {
        return !ok;
    }
}
