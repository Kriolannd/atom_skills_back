package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.Lesson;

public interface LessonsRepository extends JpaRepository<Lesson, String> {
}
