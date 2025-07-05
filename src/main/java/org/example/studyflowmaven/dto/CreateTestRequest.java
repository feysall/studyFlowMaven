package org.example.studyflowmaven.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTestRequest {
    private String title;
    private List<QuestionDto> questions;

}
