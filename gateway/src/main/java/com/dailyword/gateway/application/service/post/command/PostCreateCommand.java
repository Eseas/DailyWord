package com.dailyword.gateway.application.service.post.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 게시글 생성 Command 클래스 (Gateway)
 * Gateway 모듈에서 module-post로 전달할 게시글 생성 명령 데이터를 담는 DTO입니다.
 * 작성자 정보, 게시글 내용, 해시태그 등의 정보를 포함하여 게시글 생성 요청을 처리합니다.
 *
 * @author DailyWord Team
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateCommand {
    /**
     * 게시글 작성자의 내부 ID
     * 회원 참조 코드를 통해 변환된 실제 회원의 데이터베이스 ID입니다.
     */
    private Long authorId;

    /**
     * 게시글 내용
     * 사용자가 작성한 게시글의 본문 내용입니다.
     */
    private String content;

    /**
     * 해시태그 목록
     * 게시글에 포함된 해시태그들의 목록입니다.
     * 각 해시태그는 문자열 형태로 저장됩니다.
     */
    private List<String> hashTas;
}
