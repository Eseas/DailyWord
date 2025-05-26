package com.dailyword.member.usecase;

import com.dailyword.member.dto.member.PatchPassword;

public interface PatchPasswordUseCase {

    void patchPassword(PatchPassword.Request request);
}
