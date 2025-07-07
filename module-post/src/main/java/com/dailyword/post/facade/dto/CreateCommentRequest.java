package com.dailyword.post.facade.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {

    String content;
    Long memberId;
}
