package org.example.studyflowmaven.Mapper;

import org.example.studyflowmaven.dto.QuestionDto;
import org.example.studyflowmaven.dto.TestDto;
import org.example.studyflowmaven.entity.AnswerOption;
import org.example.studyflowmaven.entity.Question;
import org.example.studyflowmaven.entity.Test;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TestMapper {

    @Mapping(target = "questions", source = "questions")
    TestDto toDto(Test test);

    @Mapping(target = "options", ignore = true) // будем задавать вручную
    QuestionDto toDto(Question question);

    default List<String> mapOptions(List<AnswerOption> options) {
        return options.stream()
                .map(AnswerOption::getText)
                .collect(Collectors.toList());
    }

    // MapStruct hook для ручного маппинга options после основного преобразования
    @AfterMapping
    default void fillOptions(@MappingTarget QuestionDto dto, Question question) {
        dto.setOptions(mapOptions(question.getOptions()));
    }
}
