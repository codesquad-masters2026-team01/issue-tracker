package com.codesquad_team01.issue_tracker.issue.service;

import com.codesquad_team01.issue_tracker.issue.domain.Assignee;
import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import com.codesquad_team01.issue_tracker.issue.domain.IssueLabel;
import com.codesquad_team01.issue_tracker.issue.dto.request.IssueWriteRequest;
import com.codesquad_team01.issue_tracker.issue.repository.AssigneeRepository;
import com.codesquad_team01.issue_tracker.issue.repository.IssueLabelRepository;
import com.codesquad_team01.issue_tracker.issue.repository.IssueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IssueWriteServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private IssueLabelRepository issueLabelRepository;

    @InjectMocks
    private IssueWriteService issueWriteService;

    @Test
    @DisplayName("이슈 작성 시 본체와 함께 담당자(2명), 라벨(1개)이 정상적으로 저장되어야 한다")
    void writeIssue_success() {
        // Given (준비)
        // 1. 요청 DTO 생성: 담당자 2명(1, 2), 라벨 1개(3)
        IssueWriteRequest request = new IssueWriteRequest(
                "테스트 제목",
                "테스트 내용",
                List.of(1L, 2L),
                List.of(3L),
                1L
        );

        // 2. 가짜 이슈 엔티티 생성 (저장 후 ID 100L을 반환한다고 가정)
        Issue savedIssue = new Issue(
                100L,
                request.title(),
                request.contents(),
                request.milestoneId(),
                1L,
                true,
                LocalDateTime.now(),
                null
        );

        // 3. Mock 행동 정의: 어떤 Issue가 들어오든 savedIssue를 반환
        given(issueRepository.save(any(Issue.class))).willReturn(savedIssue);

        // When (실행)
        Long resultId = issueWriteService.writeIssue(request, List.of());

        // Then (검증)
        // 1. 반환된 ID가 Mock이 준 100L과 일치하는지 확인
        assertThat(resultId).isEqualTo(100L);

        // 2. 레포지토리 호출 횟수 검증
        // - 이슈 본체는 1번 저장되어야 함
        verify(issueRepository, times(1)).save(any(Issue.class));

        // - 담당자는 리스트 크기만큼(2번) 저장되어야 함 (Strategy 2)
        verify(assigneeRepository, times(2)).save(any(Assignee.class));

        // - 라벨은 리스트 크기만큼(1번) 저장되어야 함 (Strategy 2)
        verify(issueLabelRepository, times(1)).save(any(IssueLabel.class));
    }
}