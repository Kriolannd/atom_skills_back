package ru.atomskills.back.dto;

import lombok.Builder;
import lombok.Data;
import ru.atomskills.back.models.TaskProgress;

@Data
@Builder
public class TaskProgressDto {

    private TaskProgress.Status status;
    private Long timeLeftMillis;

    public static TaskProgressDto fromEntity(TaskProgress taskProgress) {
        Integer totalTimeMillis = taskProgress.getTask().getTime() * 60 * 1000;
        return TaskProgressDto.builder()
                .status(taskProgress.getStatus())
                .timeLeftMillis(taskProgress.getStatus() == TaskProgress.Status.IN_PROGRESS ?
                        taskProgress.getStartTime() + totalTimeMillis - System.currentTimeMillis() : null)
                .build();
    }
}
