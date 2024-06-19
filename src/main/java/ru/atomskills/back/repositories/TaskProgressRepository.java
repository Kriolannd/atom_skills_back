package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.TaskProgress;

import java.util.Optional;

public interface TaskProgressRepository extends JpaRepository<TaskProgress, Integer> {
    Optional<TaskProgress> findByTaskCodeAndUserId(String code, Integer userId);
}
