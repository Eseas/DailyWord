package com.dailyword.post.application.usecase.comment;

import com.dailyword.post.application.usecase.command.DeleteCommentCommand;

public interface DeleteCommentUsecase {
    void deleteComment(DeleteCommentCommand command);
}
