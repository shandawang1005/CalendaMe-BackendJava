package com.calendame.backend.controllers;

import com.calendame.backend.models.Friend;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.FriendRepository;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    // ✅ 获取所有用户
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // ✅ 创建新用户
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ 根据 ID 获取用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ 搜索用户（用户名或邮箱匹配）
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchUsers(@RequestParam String query) {
        Long currentUserId = 1L; // ⚠️ 模拟当前用户 ID
        Optional<User> currentUserOpt = userRepository.findById(currentUserId);
        if (currentUserOpt.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User currentUser = currentUserOpt.get();

        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
        List<Map<String, Object>> results = new ArrayList<>();

        for (User user : users) {
            if (user.getId().equals(currentUser.getId())) continue;

            Map<String, Object> item = new HashMap<>();
            item.put("id", user.getId());
            item.put("username", user.getUsername());
            item.put("email", user.getEmail());

            boolean isFriend = friendRepository.existsByUserAndFriendAndAcceptedTrue(currentUser, user)
                    || friendRepository.existsByUserAndFriendAndAcceptedTrue(user, currentUser);
            item.put("isFriend", isFriend);

            boolean requestSent = friendRepository.existsByUserAndFriendAndAcceptedFalse(currentUser, user);
            boolean requestReceived = friendRepository.existsByUserAndFriendAndAcceptedFalse(user, currentUser);
            item.put("requestSent", requestSent);
            item.put("requestReceived", requestReceived);

            Optional<Friend> request = friendRepository.findByUserAndFriendAndAcceptedFalse(user, currentUser);
            item.put("friendshipId", request.map(Friend::getId).orElse(null));

            results.add(item);
        }

        return ResponseEntity.ok(results);
    }

    // ✅ 取消好友请求
    @DeleteMapping("/cancel_request/{friendId}")
    public ResponseEntity<Map<String, String>> cancelFriendRequest(@PathVariable Long friendId) {
        Long currentUserId = 1L; // ⚠️ 模拟当前用户 ID
        Optional<User> currentUserOpt = userRepository.findById(currentUserId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (currentUserOpt.isEmpty() || friendOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid user"));

        User currentUser = currentUserOpt.get();
        User friend = friendOpt.get();

        Optional<Friend> friendRequest = friendRepository.findByUserAndFriendAndAcceptedFalse(currentUser, friend);

        if (friendRequest.isPresent()) {
            friendRepository.delete(friendRequest.get());
            return ResponseEntity.ok(Map.of("message", "Friend request canceled"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No pending friend request found"));
    }
}
