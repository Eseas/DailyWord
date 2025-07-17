package com.dailyword.gateway.application.usecase.mypage;

import com.dailyword.gateway.dto.mypage.MypageMainResponse;

public interface GetMypageMainInfoUsecase {
    MypageMainResponse getMypageMainInfo(String refCode);
}
