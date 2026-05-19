package com.codesquad_team01.issue_tracker.milestone;

import com.codesquad_team01.issue_tracker.issue.domain.Milestone;
import org.springframework.data.repository.ListCrudRepository;

// TODO: Hana의 코드와 충돌을 방지하기 위한 임시 레포지토리 인터페이스
public interface MilestoneTempRepository extends ListCrudRepository<Milestone, Long> {
}
