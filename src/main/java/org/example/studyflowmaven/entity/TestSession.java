package org.example.studyflowmaven.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long testId;

    private LocalDateTime startTime;

    // Время в минутах, отведенное на тест
    private Integer durationMinutes;

    private Boolean finished = false;
}
