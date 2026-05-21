package com.codesquad_team01.issue_tracker.issue.repository;

import com.codesquad_team01.issue_tracker.global.exception.ErrorCode;
import com.codesquad_team01.issue_tracker.global.exception.IssueTrackerException;
import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

import java.util.Optional;

public interface IssueRepository extends ListCrudRepository<Issue, Long> {

    Optional<Issue> findByIdAndDeletedAtIsNull(Long id);

    List<Issue> findAllByIsOpenedAndDeletedAtIsNullOrderByCreatedAtDesc(boolean isOpened);

    default List<Issue> findList(boolean isOpened) {
        return findAllByIsOpenedAndDeletedAtIsNullOrderByCreatedAtDesc(isOpened);
    }

    long countByIsOpenedAndDeletedAtIsNull(boolean isOpened);

    int countByMilestoneIdAndDeletedAtIsNull(Long milestoneId);

    int countByMilestoneIdAndIsOpenedAndDeletedAtIsNull(Long milestoneId, boolean isOpened);

    default Issue getOrThrow(Long issueId) {
        return findByIdAndDeletedAtIsNull(issueId)
                .orElseThrow(() -> new IssueTrackerException(ErrorCode.CAN_NOT_FOUND_THE_PAGE));
    }
}