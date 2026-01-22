package com.dailyword.member.application.usecase.command;

import com.dailyword.member.domain.model.SocialLoginType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterMemberCommand {
    private final String name;
    private final String nickname;
    private final String loginId;
    private final String email;
    private final SocialLoginType socialLoginType;
}
