package com.dailyword.member.application.usecase;

import com.dailyword.member.dto.member.PatchPassword;

public interface PatchPasswordUseCase {

    void patchPassword(PatchPassword.Request request);
}
