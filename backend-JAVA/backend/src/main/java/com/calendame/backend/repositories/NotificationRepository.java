package com.calendame.backend.repositories;

import com.calendame.backend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // 可以扩展：List<Notification> findByUserId(Long userId);
}
