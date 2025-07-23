package com.dailyword.post.application.usecase.post;

import com.dailyword.post.adapter.in.facade.dto.PostPageResponse;

import java.util.List;

public interface PostPageUsecase {
    List<PostPageResponse> getPosts(int page, int size);
}
