package ru.atomskills.back.dto;

import lombok.Builder;
import lombok.Data;
import ru.atomskills.back.models.Task;

import java.util.List;

@Data
@Builder
public class TaskDto {

    private String code;
    private String title;
    private String content;
    private List<Task.Supplement> supplement;
    private Integer difficulty;
    private Integer time;
    private List<LessonShortDto> lessons;

    public static TaskDto fromEntity(Task task) {
        return TaskDto.builder()
                .code(task.getCode())
                .title(task.getTitle())
                .content(task.getContent())
                .supplement(task.getSupplement())
                .difficulty(task.getDifficulty())
                .time(task.getTime())
                .lessons(task.getLessons().stream().map(LessonShortDto::fromEntity).toList())
                .build();
    }
}
