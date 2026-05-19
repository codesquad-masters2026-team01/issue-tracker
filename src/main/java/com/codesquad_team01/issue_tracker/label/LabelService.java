package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.issue.domain.Label;
import com.codesquad_team01.issue_tracker.label.dto.LabelMetaData;
import com.codesquad_team01.issue_tracker.label.dto.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.dto.LabelTempResponse;
import com.codesquad_team01.issue_tracker.milestone.MilestoneTempRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    private final LabelTempRepository labelRepository;
    private final MilestoneTempRepository milestoneRepository;

    public LabelService(LabelTempRepository labelRepository, MilestoneTempRepository milestoneRepository) {
        this.labelRepository = labelRepository;
        this.milestoneRepository = milestoneRepository;
    }

    public LabelPageResponse getLabelPageResponse(){

        // TODO: 안전한 형변환 전략 필요
        List<Label> labels = labelRepository.findAll();
        long milestoneCount = milestoneRepository.count();

        LabelMetaData labelMetaData = new LabelMetaData(labels.size(), milestoneCount);
        List<LabelTempResponse> labelTempResponses = convertLabelsToDto(labels);

        return new LabelPageResponse(labelMetaData, labelTempResponses);
    }

    private List<LabelTempResponse> convertLabelsToDto(List<Label> labels){
        return labels.stream()
                .map(LabelTempResponse::from)
                .toList();
    }
}
