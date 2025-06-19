package com.dailyword.post.application.usecase;

import com.dailyword.post.application.usecase.command.DeletePostCommand;

public interface PostDeleteUsecase {
    String delete(DeletePostCommand command);
}
