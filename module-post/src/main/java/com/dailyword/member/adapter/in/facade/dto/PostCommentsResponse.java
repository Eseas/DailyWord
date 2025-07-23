package com.dailyword.member.adapter.in.facade.dto;

import com.dailyword.member.repository.projection.CommentView;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostCommentsResponse {
    Long postId;
    List<Comment> comments;

    private PostCommentsResponse(Long postId, List<Comment> comments) {
        this.postId = postId;
        this.comments = comments;
    }

    public PostCommentsResponse toDto(Long postId, List<CommentView> commentViews) {
        List<Comment> comments = commentViews.stream().map(Comment::new).toList();

        return new PostCommentsResponse(postId, comments);
    }

    public class Comment {
        Long commentId;
        String content;
        String nickname;
        LocalDateTime createdAt;

        public Comment(CommentView commentView) {
            this.commentId = commentView.getCommnetId();
            this.content = commentView.getContent();
            this.nickname = commentView.getNickname();
            this.createdAt = commentView.getCreatedAt();
        }
    }
}
