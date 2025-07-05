package org.example.studyflowmaven.controller;

import lombok.RequiredArgsConstructor;
import org.example.studyflowmaven.Mapper.TestMapper;
import org.example.studyflowmaven.dto.CreateTestRequest;
import org.example.studyflowmaven.dto.TestDto;
import org.example.studyflowmaven.dto.UserAnswerDto.TestResultDto;
import org.example.studyflowmaven.entity.Test;
import org.example.studyflowmaven.entity.TestSession;
import org.example.studyflowmaven.repository.TestSessionRepository;
import org.example.studyflowmaven.service.TestService;
import org.example.studyflowmaven.service.TestSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final TestMapper testMapper;
    private final TestSessionRepository testSessionRepository;
    private final TestSessionService testSessionService;

    @GetMapping
    public ResponseEntity<List<TestDto>> getAllTests() {
        List<Test> tests = testService.getAllTests();
        List<TestDto> dtos = tests.stream().map(testMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTestById(@PathVariable Long id) {
        Test test = testService.getTestById(id);
        return ResponseEntity.ok(testMapper.toDto(test));
    }

    //FOR DASHBOARD
    //SUBMIT BUTTON
    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitTest(
            @PathVariable Long id,
            @RequestParam("sessionId") Long sessionId,
            @RequestBody Map<String, Integer> answers
    ) {
        try {
            //IF SESSION EXISTS?
            TestSession session = testSessionRepository.findById(sessionId)
                    .orElseThrow(() -> new RuntimeException("Сессия не найдена"));

            //Map<String,Integer> -> Map<Long,Integer>
            Map<Long, Integer> parsedAnswers = answers.entrySet().stream()
                    .filter(e -> e.getKey() != null && !"null".equalsIgnoreCase(e.getKey()))
                    .collect(Collectors.toMap(
                            e -> Long.parseLong(e.getKey()),
                            Map.Entry::getValue
                    ));

            int score = testService.checkUserAnswers(id, parsedAnswers);
            int total = testService.getTestById(id).getQuestions().size();

            return ResponseEntity.ok(new TestResultDto(score, total));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при обработке теста: " + e.getMessage());
        }
    }

    @PostMapping
    //PREAUTHORIZE FOR ADMIN??
    public ResponseEntity<?> createTest(@RequestBody CreateTestRequest request) {
        try {
            Test test = testService.createTest(request);
            return ResponseEntity.ok(testMapper.toDto(test));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при создании теста: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }

}
