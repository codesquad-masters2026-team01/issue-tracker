package com.codesquad_team01.issue_tracker.label;

import com.codesquad_team01.issue_tracker.label.domain.Label;
import com.codesquad_team01.issue_tracker.label.dto.response.LabelPageResponse;
import com.codesquad_team01.issue_tracker.label.repository.LabelRepository;
import com.codesquad_team01.issue_tracker.label.service.LabelService;
import com.codesquad_team01.issue_tracker.milestone.repository.MilestoneRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
            "라벨 엔티티의 리스트 그리고 마일스톤 엔티티의 전체 개수를 LabelPageResponse 객체로 조립하여 반환한다.")
    public void getLabelPageResponseTest(){
        List<Label> dummyLabels = List.of(
            new Label(1L, "라벨1", "설명1", "#000000", "#111111", null),
                new Label(2L, "라벨2", "설명2", "#000000", "#111111", null)
        );
        long dummyMilestoneCount = 2L;

        given(labelRepository.findAll()).willReturn(dummyLabels);
        given(milestoneRepository.count()).willReturn(dummyMilestoneCount);

        LabelPageResponse labelPageResponse = labelService.getLabelPageResponse();

        assertThat(labelPageResponse.metadata().labelCount()).isEqualTo(dummyLabels.size());
        assertThat(labelPageResponse.metadata().milestoneCount()).isEqualTo(dummyMilestoneCount);
        assertThat(labelPageResponse.labels().getFirst().name()).isEqualTo("라벨1");
        assertThat(labelPageResponse.labels().get(1).name()).isEqualTo("라벨2");
    }
}