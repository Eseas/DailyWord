package com.dailyword.member.application.usecase.post;

import com.dailyword.member.application.usecase.command.UpdatePostCommand;

public interface PostUpdateUsecase {
    String update(UpdatePostCommand command);
}
