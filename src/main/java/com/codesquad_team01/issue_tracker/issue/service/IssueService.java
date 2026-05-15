package com.codesquad_team01.issue_tracker.issue.service;

import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import com.codesquad_team01.issue_tracker.issue.dto.response.*;
import com.codesquad_team01.issue_tracker.issue.repository.IssueRepository;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelResponse;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import com.codesquad_team01.issue_tracker.member.domain.Member;
import com.codesquad_team01.issue_tracker.member.dto.response.AuthorResponse;
import com.codesquad_team01.issue_tracker.member.repository.MemberRepository;
import com.codesquad_team01.issue_tracker.milestone.domain.Milestone;
import com.codesquad_team01.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;
    private final MilestoneRepository milestoneRepository;

    public IssueService(IssueRepository issueRepository, LabelRepository labelRepository,
                        MemberRepository memberRepository, MilestoneRepository milestoneRepository ) {
        this.issueRepository = issueRepository;
        this.labelRepository = labelRepository;
        this.memberRepository = memberRepository;
        this.milestoneRepository = milestoneRepository;
    }

    public IssueListResponse getIssueList(boolean isOpened) {

        List<Issue> issueList = issueRepository.findAllByIsOpened(isOpened);

        if (issueList.isEmpty()) {
            return new IssueListResponse(createMetadata(), Collections.emptyList());
        }

        List<Long> authorIds = issueList.stream().map(Issue::authorId).distinct().toList();
        List<Long> milestoneIds = issueList.stream().map(Issue::milestoneId).filter(Objects::nonNull).distinct().toList();
        List<Long> issueIds = issueList.stream().map(Issue::id).toList();

        Map<Long, AuthorResponse> authorMap = memberRepository.findAllById(authorIds).stream()
                .collect(Collectors.toMap(Member::getId, m -> new AuthorResponse(m.getId(), m.getName())));

        Map<Long, MilestoneResponse> milestoneMap = milestoneRepository.findAllById(milestoneIds).stream()
                .collect(Collectors.toMap(Milestone::getId, m -> new MilestoneResponse(m.getId(), m.getName())));

        Map<Long, List<LabelResponse>> labelsMap = labelRepository.findAllByIssueIdIn(issueIds).stream()
                .collect(Collectors.groupingBy(
                        LabelRepository.LabelWithIssueId::issueId,
                        Collectors.mapping(l -> new LabelResponse(l.id(), l.name(), l.backgroundColor(), l.textColor()), Collectors.toList())
                ));
        List<IssueResponse> issues = issueList.stream()
                .map(issue -> {
                    AuthorResponse author = authorMap.getOrDefault(issue.authorId(),
                            new AuthorResponse(null, "알 수 없는 사용자"));
                    MilestoneResponse milestone = (issue.milestoneId() != null) ?
                            milestoneMap.get(issue.milestoneId()) : null;
                    List<LabelResponse> labels = labelsMap.getOrDefault(issue.id(), Collections.emptyList());
                    return convertToIssueResponse(issue, author, labels, milestone);
                })
                .toList();

        return new IssueListResponse(createMetadata(), issues);
    }

    private IssueListResponse.Metadata createMetadata() {
        return new IssueListResponse.Metadata(
                issueRepository.countByIsOpenedAndDeletedAtIsNull(true),
                issueRepository.countByIsOpenedAndDeletedAtIsNull(false),
                labelRepository.countByDeletedAtIsNull(),
                milestoneRepository.countByDeletedAtIsNull()
        );
    }

    private IssueResponse convertToIssueResponse(Issue issue, AuthorResponse author,
                                                 List<LabelResponse> labels, MilestoneResponse milestone) {
        return new IssueResponse(
                issue.id(),
                issue.title(),
                author,
                labels,
                milestone,
                issue.isOpened(),
                issue.createdAt()
        );
    }
}