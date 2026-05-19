package com.codesquad_team01.issue_tracker.issue.service;

import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import com.codesquad_team01.issue_tracker.issue.dto.response.IssueListResponse;
import com.codesquad_team01.issue_tracker.issue.dto.response.IssueResponse;
import com.codesquad_team01.issue_tracker.issue.repository.IssueRepository;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelResponse;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import com.codesquad_team01.issue_tracker.member.dto.response.AuthorResponse;
import com.codesquad_team01.issue_tracker.member.repository.MemberRepository;
import com.codesquad_team01.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import com.codesquad_team01.issue_tracker.global.exception.ErrorCode;
import com.codesquad_team01.issue_tracker.global.exception.IssueTrackerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;
    private final MilestoneRepository milestoneRepository;

    @Transactional
    public void deleteIssue(Long issueId) {
        if (issueId == null || issueId <= 0) {
            throw new IssueTrackerException(ErrorCode.INVALID_QUERY_MESSAGE);
        }

        Issue issue = issueRepository.findByIdAndDeletedAtIsNull(issueId)
                .orElseThrow(() -> new IssueTrackerException(ErrorCode.CAN_NOT_FOUND_THE_PAGE));

        issue.delete();
        issueRepository.save(issue);
    }

    @Transactional
    public void patchIssue(Long issueId, String status) {
        if (issueId == null || issueId <= 0) {
            throw new IssueTrackerException(ErrorCode.INVALID_QUERY_MESSAGE);
        }

        Issue issue = issueRepository.findByIdAndDeletedAtIsNull(issueId)
                .orElseThrow(() -> new IssueTrackerException(ErrorCode.CAN_NOT_FOUND_THE_PAGE));

        boolean isOpened = status.equalsIgnoreCase("OPEN");
        issue.changeStatus(isOpened);

        issueRepository.save(issue);
    }

    @Transactional
    public void titleChange(Long issueId, String title) {
        if (issueId == null || issueId <= 0) {
            throw new IssueTrackerException(ErrorCode.INVALID_QUERY_MESSAGE);
        }

        Issue issue = issueRepository.findByIdAndDeletedAtIsNull(issueId)
                .orElseThrow(() -> new IssueTrackerException(ErrorCode.CAN_NOT_FOUND_THE_PAGE));

        issue.changeTitle(title);

        issueRepository.save(issue);

    }


    @Transactional(readOnly = true)
    public IssueListResponse getIssueList(boolean isOpened) {

        long openIssueCount = issueRepository.countByIsOpenedAndDeletedAtIsNull(true);
        long closedIssueCount = issueRepository.countByIsOpenedAndDeletedAtIsNull(false);
        long labelCount = labelRepository.countByDeletedAtIsNull();
        long milestoneCount = milestoneRepository.countByDeletedAtIsNull();

        IssueListResponse.Metadata metadata = new IssueListResponse.Metadata(
                openIssueCount, closedIssueCount, labelCount, milestoneCount
        );

        List<Issue> issues = issueRepository.findList(isOpened);
        if (issues.isEmpty()) {
            return new IssueListResponse(metadata, List.of());
        }

        List<Long> issueIds = new ArrayList<>();
        List<Long> authorIds = new ArrayList<>();
        List<Long> milestoneIds = new ArrayList<>();

        for (Issue issue : issues) {
            issueIds.add(issue.getId());
            authorIds.add(issue.getAuthorId());
            if (issue.getMilestoneId() != null) {
                milestoneIds.add(issue.getMilestoneId());
            }
        }

        Map<Long, AuthorResponse> authorMap = new HashMap<>();
        memberRepository.findAllById(authorIds).forEach(m ->
                authorMap.put(m.getId(), new AuthorResponse(m.getId(), m.getName()))
        );

        Map<Long, MilestoneResponse> milestoneMap = new HashMap<>();
        milestoneRepository.findAllById(milestoneIds).forEach(ms ->
                milestoneMap.put(ms.getId(), new MilestoneResponse(ms.getId(), ms.getName()))
        );

        Map<Long, List<LabelResponse>> labelMap = new HashMap<>();
        for (LabelRepository.LabelWithIssueId row : labelRepository.findAllByIssueIdIn(issueIds)) {
            labelMap.computeIfAbsent(row.issueId(), k -> new ArrayList<>())
                    .add(new LabelResponse(row.id(), row.name(), row.backgroundColor(), row.textColor()));
        }

        List<IssueResponse> issueResponses = new ArrayList<>();
        for (Issue issue : issues) {
            issueResponses.add(new IssueResponse(
                    issue.getId(),
                    issue.getTitle(),
                    authorMap.get(issue.getAuthorId()),
                    labelMap.getOrDefault(issue.getId(), List.of()),
                    milestoneMap.get(issue.getMilestoneId()),
                    issue.isOpened(),
                    issue.getCreatedAt()
            ));
        }

        return new IssueListResponse(metadata, issueResponses);
    }

}