package ru.serpov.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.serpov.todolist.model.TaskDto;
import ru.serpov.todolist.service.TaskService;
import ru.serpov.todolist.util.JsonUtil;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tasks")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер задач", description = "контроллер для проведения операций с задачами")
public class TaskController {

    TaskService taskService;

    @PostMapping("/create")
    @Operation(description = "добавить новую задачу")
    public ResponseEntity<TaskDto> createTask(@RequestHeader("X-Request-ID")
                                              @Parameter(name = "X-Request-ID", in = HEADER, description = "ID запроса") String requestId,
                                              @Valid @RequestBody TaskDto request) {
        try (MDC.MDCCloseable ignored = MDC.putCloseable("rqUid", requestId)) {
            log.info("Got request on POST /tasks/create: {}", JsonUtil.toPrettyJson(request));
            TaskDto createdTask = taskService.createTask(request);
            log.info("Send response on /tasks/create: {}", JsonUtil.toPrettyJson(createdTask));
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{taskId}/complete")
    @Operation(description = "пометить задачу как выполненную")
    public ResponseEntity<TaskDto> markTaskDone(@RequestHeader("X-Request-ID")
                                                @Parameter(name = "X-Request-ID", in = HEADER, description = "ID запроса") String requestId,
                                                @PathVariable Long taskId) {
        try (MDC.MDCCloseable ignored = MDC.putCloseable("rqUid", requestId)) {
            log.info("Got request on PUT /tasks/{}/complete", taskId);
            TaskDto updatedTask = taskService.completeTask(taskId);
            if (updatedTask == null) {
                log.info("Send response on /tasks/{}/complete: 404 Not found", taskId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("Send response on /tasks/{}/complete: {}", taskId, JsonUtil.toPrettyJson(updatedTask));
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }
    }

    @GetMapping("/uncompleted")
    @Operation(description = "получить список невыполненных задач")
    public ResponseEntity<List<TaskDto>> getUncompletedTasks(@RequestHeader("X-Request-ID")
                                                             @Parameter(name = "X-Request-ID", in = HEADER, description = "ID запроса") String requestId) {
        try (MDC.MDCCloseable ignored = MDC.putCloseable("rqUid", requestId)) {
            log.info("Got request on GET /tasks/uncompleted");
            List<TaskDto> tasks = taskService.getUncompletedTasks();
            log.info("Send response on /tasks/uncompleted: {}", JsonUtil.toPrettyJson(tasks));
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{taskId}")
    @Operation(description = "удалить задачу")
    public ResponseEntity<Void> deleteTask(@RequestHeader("X-Request-ID")
                                           @Parameter(name = "X-Request-ID", in = HEADER, description = "ID запроса") String requestId,
                                           @PathVariable Long taskId) {
        try (MDC.MDCCloseable ignored = MDC.putCloseable("rqUid", requestId)) {
            log.info("Got request on DELETE /tasks/{}", taskId);
            taskService.deleteTask(taskId);
            log.info("Task with id {} deleted successfully", taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Got error while processing", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
