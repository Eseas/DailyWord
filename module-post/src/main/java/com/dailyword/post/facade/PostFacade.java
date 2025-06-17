package com.dailyword.post.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.post.application.usecase.PostCreateUsecase;
import com.dailyword.post.application.usecase.PostPageUsecase;
import com.dailyword.post.application.usecase.PostReadUsecase;
import com.dailyword.post.facade.dto.PostCreateRequest;
import com.dailyword.post.facade.dto.PostDetailResponse;
import com.dailyword.post.facade.dto.PostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/post")
@RequiredArgsConstructor
public class PostFacade {

    private final PostPageUsecase postPageUsecase;
    private final PostCreateUsecase postCreateUsecase;
    private final PostReadUsecase postReadUsecase;

    @GetMapping
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postPageUsecase.getPosts(page, size)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(@PathVariable Long postId) {
        PostDetailResponse response = postReadUsecase.getPost(postId);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postCreateUsecase.createPost(request.toCommand());
    }
}
