package ru.atomskills.back.dto;

import lombok.Builder;
import lombok.Data;
import ru.atomskills.back.models.Lesson;
import ru.atomskills.back.models.Trait;

import java.util.List;

@Data
@Builder
public class LessonShortDto {

    private String code;
    private String title;
    private List<Trait> traits;
    private String author;

    public static LessonShortDto fromEntity(Lesson lesson) {
        return LessonShortDto.builder()
                .code(lesson.getCode())
                .title(lesson.getTitle())
                .traits(lesson.getTraits())
                .author(lesson.getAuthor())
                .build();
    }
}
