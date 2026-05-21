package com.codesquad_team01.issue_tracker.milestone.service;
import com.codesquad_team01.issue_tracker.milestone.domain.MilestoneState;
import com.codesquad_team01.issue_tracker.milestone.dto.response.MilestoneListResponse;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

@Service
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public MilestoneListResponse findMilestones(MilestoneState state){
        return new MilestoneListResponse(milestoneRepository.findAllByState(state));
    }
}