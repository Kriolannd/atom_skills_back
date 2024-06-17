package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atomskills.back.models.Trait;

public interface TraitsRepository extends JpaRepository<Trait, String> {
}
