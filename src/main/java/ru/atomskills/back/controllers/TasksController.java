package ru.atomskills.back.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.atomskills.back.dto.TaskDto;
import ru.atomskills.back.dto.TaskProgressDto;
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

    @GetMapping("/{code}/status")
    public TaskProgressDto getStatus(@PathVariable String code) {
        return TaskProgressDto.fromEntity(tasksService.getStatus(code));
    }

    @PostMapping("/{code}/start")
    public void startTask(@PathVariable String code) {
        tasksService.startTask(code);
    }

    @PostMapping(value = "/{code}/submit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void submitTask(@PathVariable String code,
                           @RequestPart("files") MultipartFile[] files,
                           @RequestParam("comments") String[] comments) {
        tasksService.submitTask(code, List.of(files), List.of(comments));
    }

}
