package com.codesquad_team01.issue_tracker.label.service;

import com.codesquad_team01.issue_tracker.label.domain.Label;
import com.codesquad_team01.issue_tracker.label.dto.request.LabelAddRequest;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelAddResponse;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository labelRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @InjectMocks
    private LabelService labelService;

    @Test
    @DisplayName("LabelRepository, MilestoneRepository로 부터 넘겨받은" +
            "레이블 엔티티의 리스트 그리고 마일스톤 엔티티의 전체 개수를 LabelPageResponse 객체로 조립하여 반환한다.")
    public void getLabelsTest(){
        List<Label> dummyLabels = List.of(
            new Label(1L, "레이블1", "설명1", "#000000", "#111111", null),
                new Label(2L, "레이블2", "설명2", "#000000", "#111111", null)
        );
        long dummyMilestoneCount = 2L;

        given(labelRepository.findAll()).willReturn(dummyLabels);
        given(milestoneRepository.count()).willReturn(dummyMilestoneCount);

        LabelPageResponse labelPageResponse = labelService.getLabels();

        assertThat(labelPageResponse.metadata().labelCount()).isEqualTo(dummyLabels.size());
        assertThat(labelPageResponse.metadata().milestoneCount()).isEqualTo(dummyMilestoneCount);
        assertThat(labelPageResponse.labels().getFirst().name()).isEqualTo("레이블1");
        assertThat(labelPageResponse.labels().get(1).name()).isEqualTo("레이블2");
    }

    @Test
    @DisplayName("삽입할 레이블 정보를 DTO로 전달하면, 서비스는 엔티티 변환 및 저장 후 LabelAddResponse DTO를 반환한다.")
    public void addLabelTest(){
        LabelAddRequest requestDto
                = new LabelAddRequest("bug", "버그 발생", "#000000", "#111111");

        Label savedEntity
                = new Label(4L, "bug", "버그 발생", "#000000",
                "#111111", null);

        given(labelRepository.save(any(Label.class))).willReturn(savedEntity);

        LabelAddResponse responseDto = labelService.addLabel(requestDto);

        assertThat(responseDto.id()).isEqualTo(4L);
        assertThat(responseDto.name()).isEqualTo("bug");
        assertThat(responseDto.description()).isEqualTo("버그 발생");
    }
}