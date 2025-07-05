package org.example.studyflowmaven.repository;

import org.example.studyflowmaven.entity.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    Optional<TestSession> findByUserIdAndTestIdAndFinishedFalse(Long userId, Long testId);
}
