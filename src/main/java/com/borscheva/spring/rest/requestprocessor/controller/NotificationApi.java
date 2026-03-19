package com.borscheva.spring.rest.requestprocessor.controller;

import com.borscheva.spring.rest.requestprocessor.dto.NotificationApiResponse;
import com.borscheva.spring.rest.requestprocessor.dto.NotificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@Tag(name = "Notifications", description = "API для создания и отправки уведомлений")
public interface NotificationApi {

    @PostMapping
    @Operation(
            summary = "Создать уведомление",
            description = "Принимает уведомление и сохраняет в outbox для последующей отправки в Kafka"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Уведомление принято в обработку",
                    content = @Content(schema = @Schema(implementation = NotificationApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(schema = @Schema(implementation = NotificationApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = NotificationApiResponse.class))
            )
    })
    ResponseEntity<NotificationApiResponse> createNotification(
            @Valid @RequestBody NotificationRequest request
    );
}