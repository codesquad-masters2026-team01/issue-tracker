package com.codesquad_team01.issue_tracker.comment.controller;


import com.codesquad_team01.issue_tracker.comment.dto.request.CommentRequest;
import com.codesquad_team01.issue_tracker.comment.dto.response.CommentResponse;
import com.codesquad_team01.issue_tracker.comment.service.CommentService;
import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.member.dto.response.AuthorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/api/issues/{issueId}/comments")
    public ApiResponse<CommentResponse> createComment(@PathVariable Long issueId,
                                              @RequestBody @Valid CommentRequest commentRequest) {


        Long commentId = commentService.createComment(issueId, commentRequest);

        AuthorResponse author = new AuthorResponse(1L, "완자");

        CommentResponse data = new CommentResponse(
                commentId,
                author,
                commentRequest.contents(),
                LocalDateTime.now(),
                true
        );

        return ApiResponse.success("댓글 작성 완료", data);
    }
}

