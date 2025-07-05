package org.example.studyflowmaven.repository;

import org.example.studyflowmaven.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long questionId);
    int countByTestId(Long questionId);
}
