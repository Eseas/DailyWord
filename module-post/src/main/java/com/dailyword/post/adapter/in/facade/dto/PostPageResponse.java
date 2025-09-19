package com.dailyword.post.adapter.in.facade.dto;

import com.dailyword.post.domain.model.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPageResponse {
    private final Long id;
    private final Long authorId;
    private final String contentPreview;
    private final LocalDateTime createdAt;
    private final int likeCount;

    private PostPageResponse(Long id, Long authorId, String contentPreview,
                            LocalDateTime createdAt, int likeCount) {
        this.id = id;
        this.authorId = authorId;
        this.contentPreview = contentPreview;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public static PostPageResponse toDto(Post post) {
        return new PostPageResponse(post.getId(), post.getAuthorId(), summarize(post.getContent()), post.getCreatedAt(), post.getLikeCount());
    }

    private static String summarize(String content) {
        return content.length() > 100 ? content.substring(0, 100) + "..." : content;
    }
}
