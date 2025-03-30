package com.calendame.backend.seeders;

import org.springframework.stereotype.Component;
import com.calendame.backend.models.Friend;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.FriendRepository;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.util.*;

@Component
@Order(2)
public class FriendSeeder implements CommandLineRunner {
    // custom the serach method
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendSeeder(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (friendRepository.count() > 0)
            return;

        User demo = userRepository.findByUsername("Demo").orElse(null);
        User marnie = userRepository.findByUsername("marnie").orElse(null);
        User bobbie = userRepository.findByUsername("bobbie").orElse(null);
        User lydia = userRepository.findByUsername("lydia").orElse(null);
        if (demo != null && marnie != null && lydia != null) {

            // 创建好友关系（user_id, friend_id, accepted）
            Friend f1 = new Friend();
            f1.setUser(demo);
            f1.setFriend(marnie);
            f1.setAccepted(true); // Demo 和 marnie 是好友

            Friend f2 = new Friend();
            f2.setUser(demo);
            f2.setFriend(bobbie);
            f2.setAccepted(false); // Demo 请求 bobbie，但还没接受

            Friend f3 = new Friend();
            f3.setUser(marnie);
            f3.setFriend(lydia);
            f3.setAccepted(true); // marnie 和 lydia 是好友
            friendRepository.saveAll(List.of(f1, f2, f3));
            System.out.println("✅ Seeded friends into PostgreSQL.");
            System.out.println(demo.getFriends().size());
        } else {
            System.out.println("⚠️ Failed to find all users for seeding friends.");
        }

    }
}
