package com.dailyword.post.application.usecase.post;

import com.dailyword.post.application.usecase.command.DeletePostCommand;

public interface PostDeleteUsecase {
    String delete(DeletePostCommand command);
}
