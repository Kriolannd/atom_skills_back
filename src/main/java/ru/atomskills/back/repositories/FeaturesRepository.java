package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.Feature;

public interface FeaturesRepository extends JpaRepository<Feature, String> {
}
