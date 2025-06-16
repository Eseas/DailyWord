package com.dailyword.gateway.application.service.post;

import com.dailyword.gateway.adapter.out.client.PostClient;
import com.dailyword.gateway.application.usecase.post.PostPageUsecase;
import com.dailyword.gateway.dto.post.PostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostPageService implements PostPageUsecase {

    private final PostClient postClient;

    @Override
    public List<PostPageResponse> getPosts(int page, int size) {
        List<PostPageResponse> response = postClient.getPosts(page, size).getData();
        return response;
    }
}
