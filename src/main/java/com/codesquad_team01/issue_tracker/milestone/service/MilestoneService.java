package com.codesquad_team01.issue_tracker.milestone.service;

import com.codesquad_team01.issue_tracker.milestone.domain.Milestone;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }
}