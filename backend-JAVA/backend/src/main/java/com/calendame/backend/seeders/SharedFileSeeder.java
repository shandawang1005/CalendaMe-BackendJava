package com.calendame.backend.seeders;

import com.calendame.backend.models.SharedFile;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.SharedFileRepository;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(7)
public class SharedFileSeeder implements CommandLineRunner {

    private final SharedFileRepository sharedFileRepository;
    private final UserRepository userRepository;

    public SharedFileSeeder(SharedFileRepository sharedFileRepository, UserRepository userRepository) {
        this.sharedFileRepository = sharedFileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (sharedFileRepository.count() > 0) return;

        User demo = userRepository.findByUsername("Demo").orElse(null);
        User lydia = userRepository.findByUsername("lydia").orElse(null);
        User bobbie = userRepository.findByUsername("bobbie").orElse(null);

        if (demo != null && lydia != null && bobbie != null) {
            SharedFile file1 = new SharedFile();
            file1.setFileUrl("https://example.com/file1.pdf");
            file1.setOwner(demo);
            file1.setFriend(lydia);

            SharedFile file2 = new SharedFile();
            file2.setFileUrl("https://example.com/file2.png");
            file2.setOwner(lydia);
            file2.setFriend(bobbie);

            sharedFileRepository.save(file1);
            sharedFileRepository.save(file2);

            System.out.println("✅ Seeded shared files into PostgreSQL.");
        } else {
            System.out.println("⚠️ Failed to seed shared files due to missing users.");
        }
    }
}
