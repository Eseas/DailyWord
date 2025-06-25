package com.dailyword.post.facade;

import com.dailyword.common.response.APIResponse;
import com.dailyword.post.application.usecase.comment.GetPostCommentsUsecase;
import com.dailyword.post.facade.dto.PostCommentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class CommentFacade {

    private final GetPostCommentsUsecase getPostCommentsUsecase;

    @GetMapping("/posts/{refCode}/comments")
    public ResponseEntity<APIResponse<PostCommentsResponse>> getComments(
            @PathVariable String refCode,
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(getPostCommentsUsecase.getComments(refCode, page, pageSize)));
    }
}
