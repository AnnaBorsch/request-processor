package com.borscheva.spring.rest.requestprocessor.dto;

import com.borscheva.spring.rest.requestprocessor.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Запрос на отправку уведомления")
public class NotificationRequest {

    @NotNull(message = "Тип уведомления обязателен")
    @Schema(
            description = "Тип уведомления",
            example = "SMS",
            allowableValues = {"SMS", "EMAIL", "PUSH", "TG_MESSAGE"}
    )
    private NotificationType type;

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(min = 1, max = 2000, message = "Сообщение должно быть от 1 до 4000 символов")
    @Schema(
            description = "Текст сообщения",
            example = "Hello World",
            minLength = 1,
            maxLength = 2000
    )
    private String message;
}