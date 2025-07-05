package org.example.studyflowmaven.dto.UserAnswerDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserSubmissionDto {
    private Long testId;
    private List<AnswerDto> answers;

    // геттеры/сеттеры

    // Вложенный DTO для каждого ответа
    @Setter
    @Getter
    public static class AnswerDto {
        private Long questionId;
        private int selectedOption;

    }
}
