package com.dailyword.post.application.usecase;

import com.dailyword.post.dto.member.LoginDto;

public interface LoginUseCase {
    LoginDto.Response login(LoginDto.Request loginDto);
}
