package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atomskills.back.models.Lesson;
import ru.atomskills.back.repositories.LessonsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonsService {

    private final LessonsRepository lessonsRepository;

    public List<Lesson> getALL() {
        return lessonsRepository.findAll();
    }

    public Lesson getOne(String code) {
        return lessonsRepository.findById(code).orElseThrow();
    }
}
