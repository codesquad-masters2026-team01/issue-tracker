package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.issue.domain.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// TODO: Hana의 코드와 충돌을 방지하기 위한 임시 레포지토리 인터페이스
public interface LabelTempRepository extends CrudRepository<Label,Long> {
    List<Label> findAll(String labelName);
}
