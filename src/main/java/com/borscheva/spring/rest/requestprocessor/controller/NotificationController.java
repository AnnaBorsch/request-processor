package com.borscheva.spring.rest.requestprocessor.controller;

import com.borscheva.spring.rest.requestprocessor.dto.NotificationRequest;
import com.borscheva.spring.rest.requestprocessor.dto.NotificationApiResponse;
import com.borscheva.spring.rest.requestprocessor.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @Override
    @PostMapping
    public ResponseEntity<NotificationApiResponse> createNotification(
            @RequestBody NotificationRequest request
    ) {
        notificationService.processRequest(request);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(NotificationApiResponse.ok("Уведомление принято к обработке"));
    }
}