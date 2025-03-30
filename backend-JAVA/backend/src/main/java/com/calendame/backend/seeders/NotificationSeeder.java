package com.calendame.backend.seeders;

import com.calendame.backend.models.Notification;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.NotificationRepository;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5) // 第五个 Seeder，插入顺序在 Invitation 后
public class NotificationSeeder implements CommandLineRunner {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationSeeder(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (notificationRepository.count() > 0)
            return;

        // 通过用户名找到用户
        User demo = userRepository.findByUsername("Demo").orElse(null);
        User lydia = userRepository.findByUsername("lydia").orElse(null);

        if (demo != null && lydia != null) {
            Notification n1 = new Notification();
            n1.setUser(demo);
            n1.setContent("Welcome to CalendaMe!");
            n1.setRead(false); // 初始未读
            n1.setNotificationType("system");

            Notification n2 = new Notification();
            n2.setUser(lydia);
            n2.setContent("You’ve been invited to Lunch.");
            n2.setRead(true); // 假设她已经读过了
            n2.setNotificationType("invitation");

            notificationRepository.save(n1);
            notificationRepository.save(n2);

            System.out.println("✅ Seeded notifications into PostgreSQL.");
        } else {
            System.out.println("⚠️ Missing users for NotificationSeeder.");
        }
    }
}
