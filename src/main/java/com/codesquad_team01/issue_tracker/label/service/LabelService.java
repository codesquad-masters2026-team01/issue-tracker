package com.codesquad_team01.issue_tracker.label.service;

import com.codesquad_team01.issue_tracker.label.domain.Label;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> findAll() {
        return labelRepository.findAll();
    }
}