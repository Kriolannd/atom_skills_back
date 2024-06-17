package ru.atomskills.back.dto;

import lombok.Builder;
import lombok.Data;
import ru.atomskills.back.models.Task;

@Data
@Builder
public class TaskShortDto {

    private String code;
    private String title;
    private Integer difficulty;

    public static TaskShortDto fromEntity(Task task) {
        return TaskShortDto.builder()
                .code(task.getCode())
                .title(task.getTitle())
                .difficulty(task.getDifficulty())
                .build();
    }

}
