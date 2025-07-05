package org.example.studyflowmaven.service;

import lombok.RequiredArgsConstructor;
import org.example.studyflowmaven.entity.TestSession;
import org.example.studyflowmaven.repository.TestSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;

    // DEFAULT TEST TIME
    private final int DEFAULT_DURATION_MINUTES = 30;

    public TestSession startTest(Long userId, Long testId) {
        // CHECK IF ALREADY EXISTS
        Optional<TestSession> existingSession = testSessionRepository.findByUserIdAndTestIdAndFinishedFalse(userId, testId);
        if (existingSession.isPresent()) {
            return existingSession.get();
        }

        // Создаем новую сессию
        TestSession session = new TestSession();
        session.setUserId(userId);
        session.setTestId(testId);
        session.setStartTime(LocalDateTime.now());
        session.setDurationMinutes(DEFAULT_DURATION_MINUTES);
        session.setFinished(false);

        return testSessionRepository.save(session);
    }

    public boolean isTestTimeExpired(TestSession session) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = session.getStartTime().plusMinutes(session.getDurationMinutes());
        return now.isAfter(endTime);
    }

    public long getRemainingTimeSeconds(TestSession session) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = session.getStartTime().plusMinutes(session.getDurationMinutes());
        return Duration.between(now, endTime).getSeconds();
    }

    public void finishSession(TestSession session) {
        session.setFinished(true);
        testSessionRepository.save(session);
    }
}
