package com.calendame.backend.models;

// 导入 Java 持久化相关注解和工具
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
// 声明 User 这个类是一个数据库实体类，对应数据库中的一张表
@Entity
@Table(name = "users") // 指定数据库中对应的表名为 "users"
public class User {

    // 主键 ID，自增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户名字段，不能为空，唯一
    @Column(nullable = false, unique = true, length = 40)
    private String username;

    // 邮箱字段，不能为空，唯一
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    // 存储加密后的密码，不能为空
    @JsonProperty("hashed_password")
    @Column(name = "hashed_password", nullable = false, length = 255)
    private String hashedPassword;

    // ===================== 关系字段 =====================

    // 用户拥有的好友关系（自己是 user）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Friend> friends = new ArrayList<>();

    // 用户发送的消息列表
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messagesSent = new ArrayList<>();

    // 用户接收的消息列表
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messagesReceived = new ArrayList<>();

    // 用户收到的通知列表
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    // 用户创建的事件列表
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    // 用户收到的邀请（自己是 invitee）
    @OneToMany(mappedBy = "invitee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations = new ArrayList<>();

    // 用户创建的文件（自己是 owner）
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharedFile> ownedFiles = new ArrayList<>();

    // 其他人分享给用户的文件（自己是 friend）
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharedFile> friendsFiles = new ArrayList<>();

    // ===================== Getter 和 Setter =====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public List<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(List<Message> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    public List<SharedFile> getOwnedFiles() {
        return ownedFiles;
    }

    public void setOwnedFiles(List<SharedFile> ownedFiles) {
        this.ownedFiles = ownedFiles;
    }

    public List<SharedFile> getFriendsFiles() {
        return friendsFiles;
    }

    public void setFriendsFiles(List<SharedFile> friendsFiles) {
        this.friendsFiles = friendsFiles;
    }
}
