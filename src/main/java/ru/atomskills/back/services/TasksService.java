package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.atomskills.back.models.AppUser;
import ru.atomskills.back.models.Lesson;
import ru.atomskills.back.models.Task;
import ru.atomskills.back.models.TaskProgress;
import ru.atomskills.back.repositories.TaskProgressRepository;
import ru.atomskills.back.repositories.TasksRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final TaskProgressRepository taskProgressRepository;

    public List<Task> getALL() {
        return tasksRepository.findAll();
    }

    public Task getOne(String code) {
        return tasksRepository.findById(code).orElseThrow();
    }

    public void startTask(String code) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskProgressRepository.save(TaskProgress.builder()
//                .id(TaskProgress.Id.fromIds(code, user.getId()))
                .task(Task.builder().code(code).build())
                .user(user)
                .status(TaskProgress.Status.IN_PROGRESS)
                .build());
    }
}
