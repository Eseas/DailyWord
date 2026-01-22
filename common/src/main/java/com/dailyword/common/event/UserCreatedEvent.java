package com.dailyword.common.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreatedEvent extends DomainEvent {

    private Long userId;
    private String nickname;
    private String email;
    private String loginId;

    @Builder
    public UserCreatedEvent(Long userId, String nickname, String email, String loginId) {
        super("USER_CREATED");
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.loginId = loginId;
    }

    public static UserCreatedEvent of(Long userId, String nickname, String email, String loginId) {
        return UserCreatedEvent.builder()
                .userId(userId)
                .nickname(nickname)
                .email(email)
                .loginId(loginId)
                .build();
    }
}
