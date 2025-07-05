package org.example.studyflowmaven.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
