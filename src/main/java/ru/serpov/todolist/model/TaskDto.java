package ru.serpov.todolist.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Для разграничения уровня бизнес-логики и уровня обращения к БД создаем отдельное DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DTO задачи")
public class TaskDto {
    @Nullable
    @Schema(description = "ID, при create не заполняется")
    private Long id;
    @NotBlank
    @Schema(description = "Задача")
    @Size(max = 1000, message = "Задача не может быть больше 1000 символов")
    private String task;
    /**
     * default = false
     */
    @Schema(description = "Задача в работе/закрыта, при create не заполняется")
    private boolean completed;
}
