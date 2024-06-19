package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.atomskills.back.models.AppUser;
import ru.atomskills.back.models.Task;
import ru.atomskills.back.models.TaskProgress;
import ru.atomskills.back.models.TaskSubmission;
import ru.atomskills.back.repositories.TaskProgressRepository;
import ru.atomskills.back.repositories.TaskSubmissionsRepository;
import ru.atomskills.back.repositories.TasksRepository;
import ru.atomskills.back.utils.FilesUtil;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final TaskProgressRepository taskProgressRepository;
    private final TaskSubmissionsRepository taskSubmissionsRepository;

    public List<Task> getALL() {
        return tasksRepository.findAll();
    }

    public Task getOne(String code) {
        return tasksRepository.findById(code).orElseThrow();
    }

    public void startTask(String code) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = tasksRepository.findById(code).orElseThrow();
        long startTime = System.currentTimeMillis();
        TaskProgress taskProgress = taskProgressRepository.save(TaskProgress.builder()
//                .id(TaskProgress.Id.fromIds(code, user.getId()))
                .task(task)
                .user(user)
                .status(TaskProgress.Status.IN_PROGRESS)
                .startTime(startTime)
                .build());
        executeTaskT(startTime + task.getTime() * 60000, taskProgress.getId());
    }

    public void submitTask(String code, List<MultipartFile> files, List<String> comments) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskProgress taskProgress = taskProgressRepository.findByTaskCodeAndUserId(code, user.getId()).orElseThrow();

        IntStream.range(0, Math.min(files.size(), comments.size()))
                .forEach(i -> processSubmission(taskProgress, files.get(i), comments.get(i)));

        taskProgress.setStatus(TaskProgress.Status.ON_REVIEW);
        taskProgressRepository.save(taskProgress);
    }

    @SneakyThrows
    private void processSubmission(TaskProgress taskProgress, MultipartFile file, String comment) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        TaskSubmission entity = taskSubmissionsRepository.save(
                TaskSubmission.builder()
                        .taskProgress(taskProgress)
                        .image(fileName)
                        .comment(comment)
                        .build()
        );
        FilesUtil.saveFile("submissions/" + entity.getId(), fileName, file);
    }

    public TaskProgress getStatus(String code) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskProgressRepository.findByTaskCodeAndUserId(code, user.getId()).orElseThrow();
    }

    @Async
    public void executeTaskT(long timestamp, Integer taskProgressId) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        scheduler.schedule(() -> {
            TaskProgress taskProgress = taskProgressRepository.findById(taskProgressId).orElseThrow();
            if (taskProgress.getStatus().equals(TaskProgress.Status.IN_PROGRESS)) {
                taskProgress.setStatus(TaskProgress.Status.REVIEWED);
                taskProgressRepository.save(taskProgress);
            }
        }, Instant.ofEpochMilli(timestamp));//today at 8 pm UTC - replace it with any timestamp in miliseconds to text
    }
}
