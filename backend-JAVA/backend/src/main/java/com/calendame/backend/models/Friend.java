package com.calendame.backend.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

// 表示这是一个数据库实体类，对应 "friends" 表
@Entity
@Table(name = "friends")

public class Friend {

    // 主键 ID，自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 外键：发起好友请求的用户 ID，对应 users 表的主键
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    
    private User user;

    // 外键：被请求成为好友的用户 ID，对应 users 表的主键
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
 
    private User friend;

    // 标记该好友请求是否已被接受，默认为 false
    @Column(nullable = false)
    private boolean accepted = false;

    // ========== Getter 和 Setter ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty("userId")
    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("friendId")
    public Long getFriend() {
        return friend != null ? friend.getId() : null;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Long getUserId() {
        return user.getId();
    }

}
