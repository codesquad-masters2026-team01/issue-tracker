package com.codesquad_team01.issue_tracker.milestone.repository;

import com.codesquad_team01.issue_tracker.milestone.domain.Milestone;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MilestoneRepository extends ListCrudRepository<Milestone, Long> {

    @Query("SELECT * FROM milestone WHERE name = :name " +
            "AND deleted_at is null")
    List<Milestone> findByName(String name);
    long countByDeletedAtIsNull();
}
