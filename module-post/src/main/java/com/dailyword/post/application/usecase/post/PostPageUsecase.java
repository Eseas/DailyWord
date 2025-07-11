package com.dailyword.post.application.usecase.post;

import com.dailyword.post.facade.dto.PostPageResponse;

import java.util.List;

public interface PostPageUsecase {
    List<PostPageResponse> getPosts(int page, int size);
}
