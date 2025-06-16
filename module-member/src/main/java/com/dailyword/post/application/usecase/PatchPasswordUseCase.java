package com.dailyword.post.application.usecase;

import com.dailyword.post.dto.member.PatchPassword;

public interface PatchPasswordUseCase {

    void patchPassword(PatchPassword.Request request);
}
