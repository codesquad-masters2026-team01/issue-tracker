package com.codesquad_team01.issue_tracker.issue.service;

import com.codesquad_team01.issue_tracker.comment.domain.Comment;
import com.codesquad_team01.issue_tracker.comment.dto.CommentResponse;
import com.codesquad_team01.issue_tracker.comment.repository.CommentRepository;
import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import com.codesquad_team01.issue_tracker.issue.dto.response.IssueDetailResponse;
import com.codesquad_team01.issue_tracker.issue.repository.IssueRepository;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelResponse;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import com.codesquad_team01.issue_tracker.member.dto.response.AuthorResponse;
import com.codesquad_team01.issue_tracker.member.repository.MemberRepository;
import com.codesquad_team01.issue_tracker.milestone.domain.Milestone;
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
public class IssueDetailService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final LabelRepository labelRepository;
    private final MilestoneRepository milestoneRepository;

    @Transactional(readOnly = true)
    public IssueDetailResponse getIssueDetail(Long issueId) {
        if (issueId == null || issueId <= 0) {
            throw new IssueTrackerException(ErrorCode.INVALID_QUERY_MESSAGE);
        }

        Issue issue = issueRepository.findByIdAndDeletedAtIsNull(issueId)
                .orElseThrow(() -> new IssueTrackerException(ErrorCode.CAN_NOT_FOUND_THE_PAGE));

        List<Comment> comments = commentRepository.findByIssueIdAndDeletedAtIsNullOrderByIdAsc(issue.getId());
        long commentCount = comments.size();

        Set<Long> memberIds = new HashSet<>();
        memberIds.add(issue.getAuthorId());

        for (Comment comment : comments) {
            memberIds.add(comment.getAuthorId());
        }

        issue.getAssignees().forEach(assignee -> memberIds.add(assignee.getMemberId()));

        Map<Long, AuthorResponse> memberMap = new HashMap<>();
        memberRepository.findAllById(memberIds).forEach(member ->
                memberMap.put(member.getId(), new AuthorResponse(member.getId(), member.getName()))
        );

        MilestoneResponse milestoneResponse = null;
        if (issue.getMilestoneId() != null) {
            Milestone milestone = milestoneRepository.findById(issue.getMilestoneId())
                    .orElseThrow(() -> new IllegalArgumentException("Milestone not found!"));

            int totalCount = issueRepository.countByMilestoneIdAndDeletedAtIsNull(issue.getMilestoneId());
            int closedIssueCount = issueRepository.countByMilestoneIdAndIsOpenedAndDeletedAtIsNull
                    (issue.getMilestoneId(), false);

            int progress = 0;
            if (totalCount > 0) {
                progress = (closedIssueCount * 100 / totalCount);
            }

            milestoneResponse = new MilestoneResponse(milestone.getId(), milestone.getName(), progress);
        }

        List<AuthorResponse> assignees = new ArrayList<>();
        issue.getAssignees().forEach(assignee -> {
            AuthorResponse response = memberMap.get(assignee.getMemberId());
            if (response != null) assignees.add(response);
        });

        List<LabelResponse> labels = new ArrayList<>();
        List<Long> labelIds = issue.getIssueLabels().stream().map(il -> il.getLabelId()).toList();
        if (!labelIds.isEmpty()) {
            labelRepository.findAllById(labelIds).forEach(label ->
                    labels.add(new LabelResponse(label.getId(), label.getName(), label.getBackgroundColor(), label.getTextColor()))
            );
        }

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            boolean isIssueAuthor = comment.getAuthorId().equals(issue.getAuthorId());

            commentResponses.add(new CommentResponse(
                    comment.getId(),
                    memberMap.get(comment.getAuthorId()),
                    comment.getContents(),
                    comment.getCreatedAt(),
                    isIssueAuthor
            ));
        }

        return new IssueDetailResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getContents(),
                issue.isOpened(),
                issue.getCreatedAt(),
                memberMap.get(issue.getAuthorId()),
                commentCount,
                commentResponses,
                assignees,
                labels,
                milestoneResponse
        );
    }
}