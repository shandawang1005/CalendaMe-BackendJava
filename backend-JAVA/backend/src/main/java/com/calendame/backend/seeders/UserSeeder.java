package com.calendame.backend.seeders;

import com.calendame.backend.models.User;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component  // 启动时自动执行
@Order(1)
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 如果用户表不为空，就不插入，避免重复
        if (userRepository.count() > 0) return;

        // 创建用户（和 Flask seed_users 类似）
        User demo = new User();
        demo.setUsername("Demo");
        demo.setEmail("demo@aa.io");
        demo.setHashedPassword("hashedpassword");  // 后续你可以改为 BCrypt 加密

        User marnie = new User();
        marnie.setUsername("marnie");
        marnie.setEmail("marnie@aa.io");
        marnie.setHashedPassword("hashedpassword");

        User bobbie = new User();
        bobbie.setUsername("bobbie");
        bobbie.setEmail("bobbie@aa.io");
        bobbie.setHashedPassword("hashedpassword");

        User lydia = new User();
        lydia.setUsername("lydia");
        lydia.setEmail("lydia@aa.io");
        lydia.setHashedPassword("hashedpassword");

        // 存入数据库（等价于 db.session.add()）
        userRepository.save(demo);
        userRepository.save(marnie);
        userRepository.save(bobbie);
        userRepository.save(lydia);

        System.out.println("✅ Seeded users into PostgreSQL.");
    }
}
