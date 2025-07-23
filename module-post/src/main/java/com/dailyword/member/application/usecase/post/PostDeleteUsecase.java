package com.dailyword.member.application.usecase.post;

import com.dailyword.member.application.usecase.command.DeletePostCommand;

public interface PostDeleteUsecase {
    String delete(DeletePostCommand command);
}
