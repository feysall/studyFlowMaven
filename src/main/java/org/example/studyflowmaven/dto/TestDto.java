package org.example.studyflowmaven.dto;

import lombok.Data;
import java.util.List;

@Data
public class TestDto {
    private Long id;
    private String title;
    private Integer durationMinutes;
    private List<QuestionDto> questions;
}
