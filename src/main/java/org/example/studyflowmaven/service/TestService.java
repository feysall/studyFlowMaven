package org.example.studyflowmaven.service;

import lombok.RequiredArgsConstructor;
import org.example.studyflowmaven.dto.CreateTestRequest;
import org.example.studyflowmaven.dto.QuestionDto;
import org.example.studyflowmaven.dto.UserAnswerDto.UserSubmissionDto;
import org.example.studyflowmaven.entity.AnswerOption;
import org.example.studyflowmaven.entity.Question;
import org.example.studyflowmaven.entity.Test;
import org.example.studyflowmaven.repository.QuestionRepository;
import org.example.studyflowmaven.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    public void deleteTestById(Long id) {
        testRepository.deleteById(id);
    }

    public int checkUserAnswers(Long testId, Map<Long, Integer> answers) {
        Test test = getTestById(testId);
        AtomicInteger correctCount = new AtomicInteger();

        test.getQuestions().forEach(question -> {
            Integer userAnswer = answers.get(question.getId());
            if (userAnswer != null && userAnswer.equals(question.getCorrectOptionIndex())) {
                correctCount.getAndIncrement();
            }
        });

        return correctCount.get();
    }

    //FOR ADMIN
    public Test createTest(CreateTestRequest request) {
        Test test = new Test();
        test.setTitle(request.getTitle());

        List<Question> questions = new ArrayList<>();
        for (QuestionDto qDto : request.getQuestions()) {
            Question question = new Question();
            question.setText(qDto.getText());

            // Преобразуем List<String> в List<AnswerOption>
            List<AnswerOption> options = qDto.getOptions().stream()
                    .map(optionText -> {
                        AnswerOption option = new AnswerOption();
                        option.setText(optionText);
                        option.setQuestion(question); // обязательно связать с вопросом
                        return option;
                    })
                    .collect(Collectors.toList());

            question.setOptions(options);
            question.setCorrectOptionIndex(qDto.getCorrectOptionIndex());
            question.setTest(test);
            questions.add(question);
        }
        test.setQuestions(questions);
        return testRepository.save(test);
    }


    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
