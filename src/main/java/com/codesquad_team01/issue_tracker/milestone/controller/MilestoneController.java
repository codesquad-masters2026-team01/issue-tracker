package com.codesquad_team01.issue_tracker.milestone.controller;

import com.codesquad_team01.issue_tracker.global.dto.ApiResponse;
import com.codesquad_team01.issue_tracker.milestone.domain.MilestoneState;
import com.codesquad_team01.issue_tracker.milestone.dto.response.MilestoneListResponse;
import com.codesquad_team01.issue_tracker.milestone.service.MilestoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public ApiResponse<MilestoneListResponse> getMilestones(
            @RequestParam("state") MilestoneState state
    ) {
        String message = state == MilestoneState.OPEN ?
                "열린 모든 마일스톤 조회 성공" : "닫힌 모든 마일스톤 조회 성공";
        return ApiResponse.success(message, milestoneService.findMilestones(state));
    }
}