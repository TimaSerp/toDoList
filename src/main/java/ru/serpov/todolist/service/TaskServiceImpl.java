package ru.serpov.todolist.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.serpov.todolist.dao.TaskRepository;
import ru.serpov.todolist.model.TaskDto;
import ru.serpov.todolist.model.TaskEntity;
import ru.serpov.todolist.util.JsonUtil;
import ru.serpov.todolist.util.TaskMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {

    TaskRepository repository;
    TaskMapper mapper;

    @Transactional
    public TaskDto createTask(TaskDto dto) {
        if (dto.getId() != null) {
            dto.setId(null);
        }
        TaskEntity entity = mapper.toEntity(dto);
        log.debug("Request entity: {}", JsonUtil.toPrettyJson(entity));
        TaskEntity createdEntity = repository.save(entity);
        log.debug("Created entity: {}", JsonUtil.toPrettyJson(createdEntity));
        return mapper.toDto(createdEntity);
    }

    @Transactional
    public TaskDto completeTask(Long taskId) {
        Optional<TaskEntity> taskFromDbOptional = repository.findById(taskId);
        if (taskFromDbOptional.isEmpty()) {
            log.warn("There's no task with id {}", taskId);
            return null;
        }
        TaskEntity taskFromDb = taskFromDbOptional.get();
        taskFromDb.setCompleted(true);
        log.debug("Task to update completed: {}", JsonUtil.toPrettyJson(taskFromDb));
        TaskEntity updatedTask = repository.save(taskFromDb);
        log.debug("Updated task: {}", JsonUtil.toPrettyJson(updatedTask));
        return mapper.toDto(updatedTask);
    }

    public List<TaskDto> getUncompletedTasks() {
        List<TaskEntity> uncompletedTasks = repository.findByCompletedFalse();
        log.debug("Uncompleted tasks' entities: {}", uncompletedTasks);
        return uncompletedTasks.stream().map(mapper::toDto).toList();
    }

    @Transactional
    public void deleteTask(Long taskId) {
        repository.deleteById(taskId);
    }
}
