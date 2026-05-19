package com.codesquad_team01.issue_tracker.issue.repository;

import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface IssueRepository extends ListCrudRepository<Issue, Long> {

    List<Issue> findAllByIsOpenedAndDeletedAtIsNullOrderByCreatedAtDesc(boolean isOpened);

    default List<Issue> findList(boolean isOpened) {
        return findAllByIsOpenedAndDeletedAtIsNullOrderByCreatedAtDesc(isOpened);
    }

    long countByIsOpenedAndDeletedAtIsNull(boolean isOpened);
}