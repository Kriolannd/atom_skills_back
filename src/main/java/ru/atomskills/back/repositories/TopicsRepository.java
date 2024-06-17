package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.Topic;

public interface TopicsRepository extends JpaRepository<Topic, String> {
}
