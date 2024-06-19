package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.TaskSubmission;

public interface TaskSubmissionsRepository extends JpaRepository<TaskSubmission, Integer> {
}
