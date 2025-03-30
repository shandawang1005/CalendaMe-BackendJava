// SharedFile.java
// 对应 shared_files 表，管理用户之间分享的文件记录

package com.calendame.backend.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "shared_files")
public class SharedFile {

    // 主键 ID，自增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 文件的 URL，不能为空
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    // 文件所有者（分享者）的 ID，外键关联 User 表
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore // 避免递归或不必要的数据暴露
    private User owner;

    // 接收文件的好友 ID，外键关联 User 表
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    @JsonIgnore
    private User friend;

    // ===== Getter / Setter 区域 =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
