package com.codesquad_team01.issue_tracker.issue.repository;

import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long> {

    @Query("SELECT * From issue where is_opened = :isOpened and deleted_at is null ORDER BY created_at DESC")
    List<Issue> findAllByIsOpened(boolean isOpened);

    boolean isOpened(boolean isOpened);
}
