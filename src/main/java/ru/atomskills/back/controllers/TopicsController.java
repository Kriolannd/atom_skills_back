package ru.atomskills.back.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atomskills.back.models.Topic;
import ru.atomskills.back.services.TopicsService;

import java.util.List;

@RestController
@RequestMapping("topics")
@RequiredArgsConstructor
public class TopicsController {

    private final TopicsService topicsService;

    @GetMapping
    public List<Topic> getAll() {
        return topicsService.getAll();
    }

}
