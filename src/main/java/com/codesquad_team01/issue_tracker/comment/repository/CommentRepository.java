package com.codesquad_team01.issue_tracker.comment.repository;

import com.codesquad_team01.issue_tracker.comment.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByIssueIdAndDeletedAtIsNullOrderByIdAsc(Long issueId);
}
