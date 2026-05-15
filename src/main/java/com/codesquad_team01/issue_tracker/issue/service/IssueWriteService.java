package com.codesquad_team01.issue_tracker.issue.service;


import com.codesquad_team01.issue_tracker.issue.domain.Assignee;
import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import com.codesquad_team01.issue_tracker.issue.domain.IssueLabel;
import com.codesquad_team01.issue_tracker.issue.dto.request.IssueWriteRequest;
import com.codesquad_team01.issue_tracker.issue.repository.AssigneeRepository;
import com.codesquad_team01.issue_tracker.issue.repository.IssueLabelRepository;
import com.codesquad_team01.issue_tracker.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueWriteService {

    private final IssueRepository issueRepository;
    private final AssigneeRepository assigneeRepository;
    private final IssueLabelRepository issueLabelRepository;

    @Transactional
    public Long writeIssue(IssueWriteRequest issueWriteRequest, List<MultipartFile> files) {

        Issue issue = new Issue(
                null,
                issueWriteRequest.title(),
                issueWriteRequest.contents(),
                issueWriteRequest.milestoneId(),
                1L,
                true,
                LocalDateTime.now(),
                null
        );
        Issue savedIssue = issueRepository.save(issue);
        Long issueId = savedIssue.id();

        for (Long memberId : issueWriteRequest.assigneeIds()) {
            assigneeRepository.save(new Assignee(null, issueId, memberId ));
        }

        for (Long labelId : issueWriteRequest.labelsIds()) {
            issueLabelRepository.save(new IssueLabel(null, issueId, labelId));
        }
        return issueId;
    }
}
