package org.example.studyflowmaven.controller;

import lombok.RequiredArgsConstructor;
import org.example.studyflowmaven.entity.TestSession;
import org.example.studyflowmaven.entity.User;
import org.example.studyflowmaven.service.TestSessionService;
import org.example.studyflowmaven.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test-sessions")
@RequiredArgsConstructor
public class TestSessionController {

    private final TestSessionService testSessionService;
    private final UserService userService;

    @PostMapping("/start/{testId}")
    public ResponseEntity<?> startTest(@PathVariable Long testId, Principal principal) {
        try {
            //FROM JWT GET USERNAME
            String username = principal.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            TestSession session = testSessionService.startTest(user.getId(), testId);

            Map<String, Object> response = new HashMap<>();
            //FORM RESPONSE
            response.put("sessionId", session.getId());
            response.put("remainingTimeSeconds", testSessionService.getRemainingTimeSeconds(session));
            response.put("durationMinutes", session.getDurationMinutes());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Ошибка при запуске теста: " + e.getMessage());
        }
    }
}
