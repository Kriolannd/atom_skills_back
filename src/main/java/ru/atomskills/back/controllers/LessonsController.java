package ru.atomskills.back.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atomskills.back.dto.LessonDto;
import ru.atomskills.back.dto.LessonShortDto;
import ru.atomskills.back.services.LessonsService;

import java.util.List;

@RestController
@RequestMapping("lessons")
@RequiredArgsConstructor
public class LessonsController {

    private final LessonsService lessonsService;

    @GetMapping
    public List<LessonShortDto> getAll() {
        return lessonsService.getALL().stream().map(LessonShortDto::fromEntity).toList();
    }

    @GetMapping("/{code}")
    public LessonDto getOne(@PathVariable String code) {
        return LessonDto.fromEntity(lessonsService.getOne(code));
    }

}
