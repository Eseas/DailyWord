package com.dailyword.member.usecase;

import com.dailyword.member.dto.member.LoginDto;

public interface LoginUseCase {
    LoginDto.Response login(LoginDto.Request loginDto);
}
