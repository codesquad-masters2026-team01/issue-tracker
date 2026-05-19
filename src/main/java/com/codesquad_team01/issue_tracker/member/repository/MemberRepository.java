package com.codesquad_team01.issue_tracker.member.repository;

import com.codesquad_team01.issue_tracker.member.domain.Member;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface MemberRepository extends ListCrudRepository<Member, Long> {

    @Query("SELECT * FROM member WHERE user_id = :userId " +
            "AND deleted_at IS NULL")
    Optional<Member> findByUserId(String userId);
}
