package com.codesquad_team01.issue_tracker.label.service;

import com.codesquad_team01.issue_tracker.label.domain.Label;
import com.codesquad_team01.issue_tracker.label.dto.request.LabelAddRequest;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelAddResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelListResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelMetaData;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LabelService {
    private final LabelRepository labelRepository;
    private final MilestoneRepository milestoneRepository;

    public LabelService(LabelRepository labelRepository, MilestoneRepository milestoneRepository) {
        this.labelRepository = labelRepository;
        this.milestoneRepository = milestoneRepository;
    }

    public LabelPageResponse getLabels(){
        List<Label> labels = labelRepository.findAll();
        long milestoneCount = milestoneRepository.count();

        LabelMetaData labelMetaData = new LabelMetaData(labels.size(), milestoneCount);
        List<LabelListResponse> labelListResponse = convertLabelsToDto(labels);

        return new LabelPageResponse(labelMetaData, labelListResponse);
    }

    private List<LabelListResponse> convertLabelsToDto(List<Label> labels){
        return labels.stream()
                .map(LabelListResponse::from)
                .toList();
    }

    public LabelAddResponse addLabel(LabelAddRequest labelAddRequest){
        Label before = labelAddRequest.toLabel();
        Label after = labelRepository.save(before);
        return LabelAddResponse.labelToResponse(after);
    }
}