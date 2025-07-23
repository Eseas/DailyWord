package com.dailyword.member.application.usecase.post;

import com.dailyword.member.adapter.in.facade.dto.PostPageResponse;

import java.util.List;

public interface PostPageUsecase {
    List<PostPageResponse> getPosts(int page, int size);
}
