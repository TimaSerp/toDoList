package ru.serpov.todolist.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.serpov.todolist.model.TaskDto;
import ru.serpov.todolist.model.TaskEntity;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", source = "task.id")
    @Mapping(target = "task", source = "task.task")
    @Mapping(target = "completed", source = "task.completed")
    TaskEntity toEntity(TaskDto task);

    @Mapping(target = "id", source = "task.id")
    @Mapping(target = "task", source = "task.task")
    @Mapping(target = "completed", source = "task.completed")
    TaskDto toDto(TaskEntity task);
}
