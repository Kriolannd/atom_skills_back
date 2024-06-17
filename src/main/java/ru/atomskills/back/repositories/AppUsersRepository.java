package ru.atomskills.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.atomskills.back.models.AppUser;

import java.util.Optional;

@Repository
public interface AppUsersRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByUsername(String username);
}
