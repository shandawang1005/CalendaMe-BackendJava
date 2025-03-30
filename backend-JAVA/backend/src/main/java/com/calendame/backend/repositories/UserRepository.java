package com.calendame.backend.repositories;

import com.calendame.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

// 让 Spring 自动实现所有的数据库操作
@Repository
// 这里的Long是Prim Key的类型

public interface UserRepository extends JpaRepository<User, Long> {
    // 你可以在这里添加自定义查询方法，例如：findByEmail(String email)
    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);

    Optional<User> findByUsername(String username);
}
