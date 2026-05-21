package com.codesquad_team01.issue_tracker.issue.dto.mapper;


import com.codesquad_team01.issue_tracker.issue.domain.Issue;
import com.codesquad_team01.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MilestoneDtoMapper {

    private final MilestoneRepository milestoneRepository;

    public Map<Long, MilestoneResponse> getMilestoneMap(List<Issue> issues) {

        List<Long> milestoneIds = new ArrayList<>();

        for (Issue issue : issues) {
            if (issue.getMilestoneId() != null) {
                milestoneIds.add(issue.getMilestoneId());
            }
        }

        Map<Long, MilestoneResponse> milestoneMap = new HashMap<>();
        milestoneRepository.findAllById(milestoneIds).forEach(ms ->
                milestoneMap.put(ms.getId(), new MilestoneResponse(ms.getId(), ms.getName()))
        );


        return milestoneMap;
    }
}
