package com.calendame.backend.controllers;

import com.calendame.backend.models.User;
import com.calendame.backend.repositories.UserRepository;
import com.calendame.backend.dto.LoginRequest;
import com.calendame.backend.dto.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 模拟 current_user
    private User currentUser = null;

    // 获取当前用户（登录态检查）
    @GetMapping("/")
    public ResponseEntity<?> authenticate() {
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Unauthorized"));
    }

    // 登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getHashedPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

        currentUser = user; // 模拟 login_user
        return ResponseEntity.ok(user);
    }

    // 登出
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        currentUser = null;
        return ResponseEntity.ok(Map.of("message", "User logged out"));
    }

    // 注册
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        Optional<User> existingUserOpt = userRepository.findByEmail(signupRequest.getEmail());

        if (existingUserOpt.isPresent()) { // 使用 Optional 的 isPresent 方法
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email already in use"));
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setHashedPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
        currentUser = user; // 模拟自动登录

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // 获取当前用户资料
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        return ResponseEntity.ok(currentUser);
    }

    // 修改当前用户资料
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> updates) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        currentUser.setUsername(updates.getOrDefault("username", currentUser.getUsername()));
        currentUser.setEmail(updates.getOrDefault("email", currentUser.getEmail()));

        userRepository.save(currentUser);
        return ResponseEntity.ok(currentUser);
    }

    // 修改密码
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> passwords) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        String oldPassword = passwords.get("old_password");
        String newPassword = passwords.get("new_password");
        String confirmPassword = passwords.get("confirm_password");

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "New passwords do not match."));
        }

        if (!passwordEncoder.matches(oldPassword, currentUser.getHashedPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Current password is incorrect."));
        }

        currentUser.setHashedPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);

        return ResponseEntity.ok(Map.of("message", "Password changed successfully."));
    }

    // 返回未授权
    @GetMapping("/unauthorized")
    public ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Unauthorized"));
    }
}
