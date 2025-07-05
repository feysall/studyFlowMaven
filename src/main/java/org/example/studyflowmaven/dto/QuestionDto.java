package org.example.studyflowmaven.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String text;
    private Integer correctOptionIndex;
    private List<String> options;
}
