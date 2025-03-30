package com.calendame.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity // 声明这是一个数据库实体类
@Table(name = "notifications") // 映射到 notifications 表
public class Notification {

    // 主键 ID，自增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 通知属于哪个用户，对应 users 表的主键
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 建立外键关联
    private User user;

    // 通知的类型，比如 'friend_request', 'appointment_update'
    @Column(name = "notification_type", nullable = false, length = 50)
    private String notificationType;

    // 通知的内容正文，必须填写
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 通知的时间戳，默认当前时间
    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    // 是否已读，默认是未读 false
    @Column(nullable = false)
    private boolean read = false;

    // ======= Getter 和 Setter 方法 =======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
