package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atomskills.back.models.Topic;
import ru.atomskills.back.repositories.TopicsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicsService {

    private final TopicsRepository topicsRepository;

    public List<Topic> getAll() {
        return topicsRepository.findAll();
    }

}
