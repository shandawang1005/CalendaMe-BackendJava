package com.calendame.backend.seeders;

import com.calendame.backend.models.Message;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.MessageRepository;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Order(6) // 第 6 个插入
public class MessageSeeder implements CommandLineRunner {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageSeeder(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (messageRepository.count() > 0)
            return;

        // 获取发送者和接收者用户
        User demo = userRepository.findByUsername("Demo").orElse(null);
        User marnie = userRepository.findByUsername("marnie").orElse(null);

        if (demo != null && marnie != null) {
            Message msg1 = new Message();
            msg1.setContent("Hey, how are you?");
            msg1.setSentAt(LocalDateTime.now());
            msg1.setSender(demo);
            msg1.setRecipient(marnie);

            Message msg2 = new Message();
            msg2.setContent("I'm doing well! How about you?");
            msg2.setSentAt(LocalDateTime.now());
            msg2.setSender(marnie);
            msg2.setRecipient(demo);

            Message msg3 = new Message();
            msg3.setContent("I'm good too. Want to catch up later?");
            msg3.setSentAt(LocalDateTime.now());
            msg3.setSender(demo);
            msg3.setRecipient(marnie);

            messageRepository.save(msg1);
            messageRepository.save(msg2);
            messageRepository.save(msg3);

            System.out.println("✅ Seeded messages into PostgreSQL.");
        } else {
            System.out.println("⚠️ Missing users for message seeding.");
        }
    }
}
