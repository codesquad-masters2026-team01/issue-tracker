package com.codesquad_team01.issue_tracker.comment.service;


import com.codesquad_team01.issue_tracker.comment.domain.Comment;
import com.codesquad_team01.issue_tracker.comment.dto.request.CommentRequest;
import com.codesquad_team01.issue_tracker.comment.repository.CommentRepository;
import com.codesquad_team01.issue_tracker.global.exception.ErrorCode;
import com.codesquad_team01.issue_tracker.global.exception.IssueTrackerException;
import com.codesquad_team01.issue_tracker.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;




    @Transactional
    public Long createComment( Long issueId, CommentRequest commentRequest) {
        if (issueId == null || issueId <= 0) {
            throw new IssueTrackerException(ErrorCode.INVALID_QUERY_MESSAGE);
        }

        issueRepository.findByIdAndDeletedAtIsNull(issueId)
                .orElseThrow(() -> new IssueTrackerException(ErrorCode.CAN_NOT_FOUND_THE_PAGE));

        Comment comment = new Comment(
                null,
                issueId,
                1L, //TODO : 고정값 변경하기
                commentRequest.contents(),
                LocalDateTime.now(),
                null
        );

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }
}
