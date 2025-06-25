package com.dailyword.post.application.usecase.post;

import com.dailyword.post.application.usecase.command.UpdatePostCommand;

public interface PostUpdateUsecase {
    String update(UpdatePostCommand command);
}
