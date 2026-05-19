package com.codesquad_team01.issue_tracker.milestone.controller;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.milestone.domain.Milestone;
import com.codesquad_team01.issue_tracker.milestone.service.MilestoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public ApiResponse<List<Milestone>> getMilestones() {
        return ApiResponse.success(milestoneService.findAll());
    }
}