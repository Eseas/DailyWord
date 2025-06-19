package com.dailyword.post.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.post.application.usecase.PostCreateUsecase;
import com.dailyword.post.application.usecase.PostPageUsecase;
import com.dailyword.post.application.usecase.PostReadUsecase;
import com.dailyword.post.application.usecase.PostUpdateUsecase;
import com.dailyword.post.facade.dto.PostCreateRequest;
import com.dailyword.post.facade.dto.PostDetailResponse;
import com.dailyword.post.facade.dto.PostPageResponse;
import com.dailyword.post.facade.dto.PostUpdateRequest;
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
    private final PostUpdateUsecase postUpdateUsecase;

    @GetMapping
    public ResponseEntity<APIResponse<List<PostPageResponse>>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.success(postPageUsecase.getPosts(page, size)));
    }

    @GetMapping("/{postRefCode}")
    public ResponseEntity<APIResponse<PostDetailResponse>> getPost(@PathVariable String postRefCode) {
        PostDetailResponse response = postReadUsecase.getPost(postRefCode);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping
    public String createPost(@RequestBody PostCreateRequest request) {
        return postCreateUsecase.createPost(request.toCommand());
    }

    @PutMapping("/{refCode}")
    public ResponseEntity<APIResponse<String>> updatePost(
            @PathVariable String refCode,
            @RequestBody PostUpdateRequest request
    ) {
        String updatedRefCode = postUpdateUsecase.update(request.toCommand(refCode));
        return ResponseEntity.ok(APIResponse.success(updatedRefCode));
    }
}
