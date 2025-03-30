package com.calendame.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity // 声明这是一个数据库实体类
@Table(name = "messages") // 指定表名为 messages
public class Message {

    // 主键 ID，自动增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 发送者 ID，对应 users 表的主键
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // 接收者 ID，对应 users 表的主键
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    // 消息内容，不允许为空
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 发送时间，默认是当前时间
    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    // ====== Getter / Setter 方法 ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
