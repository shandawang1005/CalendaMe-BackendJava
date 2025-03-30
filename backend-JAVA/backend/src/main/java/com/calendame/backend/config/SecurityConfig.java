package com.calendame.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 创建 PasswordEncoder Bean，使用 BCrypt 加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置 SecurityFilterChain 来替代 WebSecurityConfigurerAdapter（Spring Security 6.x 新方式）
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 配置 - 默认开启，如果不需要可以禁用
            .csrf(csrf -> csrf.disable())  // 禁用 CSRF 保护

            // 配置授权规则
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()  // 允许所有访问 /api/auth/**
                .anyRequest().authenticated()  // 其他请求需要认证
            )

            // 禁用默认的表单登录界面
            .formLogin(form -> form.disable())  // 禁用默认登录页面

            // 禁用 HTTP 基本认证（HTTP Basic）
            .httpBasic(httpBasic -> httpBasic.disable());  // 禁用 HTTP 基本认证

        return http.build();  // 返回配置的 HttpSecurity
    }
}
