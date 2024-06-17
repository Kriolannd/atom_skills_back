package ru.atomskills.back.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.atomskills.back.models.SomeEntity;

@Repository
public interface SomeEntityRepository extends JpaRepository<SomeEntity, Integer> {
    Page<SomeEntity> findAll(Specification<SomeEntity> specification, Pageable pageable);
}
