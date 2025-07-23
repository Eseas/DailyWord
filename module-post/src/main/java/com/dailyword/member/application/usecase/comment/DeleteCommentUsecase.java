package com.dailyword.member.application.usecase.comment;

import com.dailyword.member.application.usecase.command.DeleteCommentCommand;

public interface DeleteCommentUsecase {
    void deleteComment(DeleteCommentCommand command);
}
