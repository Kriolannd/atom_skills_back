package ru.atomskills.back.dto;

import lombok.Builder;
import lombok.Data;
import ru.atomskills.back.models.Lesson;
import ru.atomskills.back.models.Task;
import ru.atomskills.back.models.Topic;
import ru.atomskills.back.models.Trait;
import ru.atomskills.back.services.LessonsService;

import java.util.List;

@Data
@Builder
public class LessonDto {

    private String code;
    private String title;
    private String content;
    private List<Trait> traits;
    private List<Lesson.Supplement> supplement;
    private List<TaskShortDto> tasks;
    private String author;
    private List<Topic> topics;

    public static LessonDto fromEntity(Lesson lesson) {
        return LessonDto.builder()
                .code(lesson.getCode())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .traits(lesson.getTraits())
                .supplement(lesson.getSupplement())
                .tasks(lesson.getTasks().stream().map(TaskShortDto::fromEntity).toList())
                .author(lesson.getAuthor())
                .topics(lesson.getTopics())
                .build();
    }
}
