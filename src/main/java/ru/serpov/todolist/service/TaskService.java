package ru.serpov.todolist.service;

import org.springframework.stereotype.Service;
import ru.serpov.todolist.model.TaskDto;

import java.util.List;

@Service
public interface TaskService {

    TaskDto createTask(TaskDto dto);

    TaskDto completeTask(Long taskId);

    List<TaskDto> getUncompletedTasks();

    void deleteTask(Long taskId);
}
