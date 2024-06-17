package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atomskills.back.models.Lesson;
import ru.atomskills.back.models.Task;
import ru.atomskills.back.repositories.TasksRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;

    public List<Task> getALL() {
        return tasksRepository.findAll();
    }

    public Task getOne(String code) {
        return tasksRepository.findById(code).orElseThrow();
    }
}
