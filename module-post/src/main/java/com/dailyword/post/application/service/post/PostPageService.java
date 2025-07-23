package com.dailyword.post.application.service.post;

import com.dailyword.post.application.usecase.post.PostPageUsecase;
import com.dailyword.post.domain.model.Post;
import com.dailyword.post.adapter.in.facade.dto.PostPageResponse;
import com.dailyword.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostPageService implements PostPageUsecase {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PostPageResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostPageResponse> result = posts.stream()
                .map(PostPageResponse::toDto).toList();

        return result;
    }
}
