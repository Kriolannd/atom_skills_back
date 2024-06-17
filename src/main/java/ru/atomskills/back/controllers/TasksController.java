package ru.atomskills.back.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atomskills.back.dto.LessonDto;
import ru.atomskills.back.dto.LessonShortDto;
import ru.atomskills.back.dto.TaskDto;
import ru.atomskills.back.dto.TaskShortDto;
import ru.atomskills.back.services.TasksService;

import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TasksController {

    private final TasksService tasksService;

    @GetMapping
    public List<TaskShortDto> getAll() {
        return tasksService.getALL().stream().map(TaskShortDto::fromEntity).toList();
    }

    @GetMapping("/{code}")
    public TaskDto getOne(@PathVariable String code) {
        return TaskDto.fromEntity(tasksService.getOne(code));
    }

}
