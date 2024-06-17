package ru.atomskills.back.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.atomskills.back.models.Lesson;
import ru.atomskills.back.models.Task;
import ru.atomskills.back.models.Topic;
import ru.atomskills.back.models.Trait;
import ru.atomskills.back.repositories.FeaturesRepository;
import ru.atomskills.back.repositories.LessonsRepository;
import ru.atomskills.back.repositories.TasksRepository;
import ru.atomskills.back.repositories.TopicsRepository;
import ru.atomskills.back.repositories.TraitsRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class FileLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final FeaturesRepository featuresRepository;
    private final TopicsRepository topicsRepository;
    private final TraitsRepository traitsRepository;
    private final TasksRepository tasksRepository;
    private final LessonsRepository lessonsRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    @SneakyThrows
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
//        File materials = new File(this.getClass().getClassLoader().getResource("materials/lessons").getFile());
//        materials

        Path materials = Paths.get(this.getClass().getClassLoader().getResource("materials").toURI());

        Map<String, String> features = objectMapper.readerFor(Map.class).readValue(materials.resolve("features.json").toFile());
//        featuresRepository.saveAll(features.entrySet().stream().map(entry -> new Feature(entry.getKey(), entry.getValue())).toList());

        Map<String, List<Path>> entitiesPaths = Map.of(
            "tasks", new ArrayList<>(),
            "lessons", new ArrayList<>(),
            "topics", new ArrayList<>()
        );

        Path lessons = materials.resolve("lessons");

        processTraits(lessons.resolve("traits.json"));

        Files.list(lessons).forEach(path -> {
            String name = path.getFileName().toString();

            if (path.toFile().isFile() && name.startsWith("topic")) {
                entitiesPaths.get("topics").add(path);
                return;
            }

            if (!path.toFile().isDirectory()) return;

            if (name.startsWith("tsk")) {
                entitiesPaths.get("tasks").add(path);
                return;
            }

            if (name.startsWith("lsn")) {
                entitiesPaths.get("lessons").add(path);
                return;
            }
        });

        entitiesPaths.get("tasks").forEach(this::processTask);
        entitiesPaths.get("lessons").forEach(this::processLesson);
        entitiesPaths.get("topics").forEach(this::processTopic);
    }

    @SneakyThrows
    private void processTraits(Path path) {
        traitsRepository.saveAll(objectMapper.readValue(path.toFile(), new TypeReference<List<Trait>>(){}));
    }

    @SneakyThrows
    private void processTask(Path path) {
        Path task = path.resolve(path.getFileName().toString() + ".json");
        Task entity = objectMapper.readValue(task.toFile(), Task.class);
        entity.getSupplement().forEach(supplement -> supplement.setTask(entity));
        tasksRepository.save(entity);
    }

    @SneakyThrows
    private void processLesson(Path path) {
        Path lesson = path.resolve(path.getFileName().toString() + ".json");
        Lesson entity = objectMapper.readValue(lesson.toFile(), Lesson.class);
        entity.getSupplement().forEach(supplement -> supplement.setLesson(entity));
        lessonsRepository.save(entity);
    }

    @SneakyThrows
    private void processTopic(Path path) {
        Topic entity = objectMapper.readValue(path.toFile(), Topic.class);
//        entity.setLessons(lessonsRepository.findAllById(entity.getLessons().stream().map(Lesson::getCode).toList()));
//        entity.getLessons().forEach(lesson -> {
//            if (lesson.getTopics() == null) {
//                lesson.setTopics(List.of(entity));
//            } else {
//                lesson.getTopics().add(entity);
//            }
//        });
        topicsRepository.save(entity);
    }
}