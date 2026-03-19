package com.borscheva.spring.rest.requestprocessor.repository;

import com.borscheva.spring.rest.requestprocessor.entity.NotificationOutbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox, UUID> {

    @Transactional(readOnly = true)
    @Query("""
            SELECT o FROM NotificationOutbox o
            WHERE o.sent = false
              AND o.attempt <= :maxAttempts
            ORDER BY o.createdAt ASC
            """)
    List<NotificationOutbox> findUnsentMessages(
            @Param("maxAttempts") int maxAttempts,
            Pageable pageable
    );

    @Transactional
    @Modifying
    @Query("UPDATE NotificationOutbox o SET o.sent = true WHERE o.id = :id")
    void markAsSent(@Param("id") UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE NotificationOutbox o SET o.attempt = o.attempt + 1 WHERE o.id = :id")
    void incrementAttempt(@Param("id") UUID id);
}
