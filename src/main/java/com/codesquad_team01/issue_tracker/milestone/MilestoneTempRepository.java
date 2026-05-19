package com.codesquad_team01.issue_tracker.milestone;

import com.codesquad_team01.issue_tracker.issue.domain.Milestone;
import org.springframework.data.repository.CrudRepository;

public interface MilestoneTempRepository extends CrudRepository<Milestone, Long> {
    long count();
}
