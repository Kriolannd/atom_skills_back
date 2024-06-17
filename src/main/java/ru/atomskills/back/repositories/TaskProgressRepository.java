package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.TaskProgress;

public interface TaskProgressRepository extends JpaRepository<TaskProgress, Integer> {
}
