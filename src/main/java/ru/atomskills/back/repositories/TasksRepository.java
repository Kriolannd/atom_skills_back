package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.Task;

public interface TasksRepository extends JpaRepository<Task, String> {
}
