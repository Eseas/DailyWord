package com.dailyword.member.application.usecase;

import com.dailyword.member.application.usecase.command.RegisterMemberCommand;

public interface RegisterMemberUseCase {
    Long register(RegisterMemberCommand command);
}
