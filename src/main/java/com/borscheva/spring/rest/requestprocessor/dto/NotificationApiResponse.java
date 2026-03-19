package com.borscheva.spring.rest.requestprocessor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ответ API")
public class NotificationApiResponse {

    @Schema(description = "Статус операции", example = "успешно")
    private String status;

    @Schema(description = "Сообщение", example = "Уведомление принято в обработку")
    private String message;

    @Schema(description = "Время ответа")
    private LocalDateTime timestamp;

    @Schema(description = "Список ошибок")
    private List<String> errors;

    public static NotificationApiResponse ok(String message) {
        NotificationApiResponse response = new NotificationApiResponse();
        response.setStatus("успешно");
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    public static NotificationApiResponse validationError(String message, List<String> errors) {
        NotificationApiResponse response = new NotificationApiResponse();
        response.setStatus("Ошибка");
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(errors);
        return response;
    }

    public static NotificationApiResponse error(String message) {
        NotificationApiResponse response = new NotificationApiResponse();
        response.setStatus("ошибка");
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
}