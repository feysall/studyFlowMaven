package org.example.studyflowmaven.repository;

import org.example.studyflowmaven.entity.Test;
import org.example.studyflowmaven.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

    Test findByTitle(String title);
}
