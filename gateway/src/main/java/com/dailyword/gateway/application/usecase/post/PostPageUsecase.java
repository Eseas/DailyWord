package com.dailyword.gateway.application.usecase.post;

import com.dailyword.gateway.dto.post.PostPageResponse;

import java.util.List;

public interface PostPageUsecase {
    List<PostPageResponse> getPosts(int page, int size);
}
