package com.codesquad_team01.issue_tracker.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentEditRequest(

        @NotBlank(message = "댓글 내용은 필수 입니다.")
        String contents
) { }
