package com.dailyword.gateway.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class QtProgressResponse {
    private List<LocalDate> qtProgressDates;
}