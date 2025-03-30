package com.calendame.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// 声明这个类是一个数据库实体，对应表名 "events"
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "events")
public class Event {

    // 主键 ID，自增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 事件标题，不能为空
    @Column(nullable = false, length = 120)
    private String title;

    // 开始时间，不能为空
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    // 结束时间，不能为空
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    // 地点，可以为空
    @Column(length = 255)
    private String location;

    // 可见性（public/private），不能为空
    @Column(nullable = false, length = 10)
    private String visibility;

    // 是否重复事件，默认 false
    @Column(nullable = false)
    private boolean recurring = false;

    // 创建人 ID，外键关联 User 表
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    // ========== 关系字段 ==========

    // 创建者用户，ManyToOne -> User（一个用户可以创建多个事件）

    // 这里是Lazy loading只有真正发生的时候才会load这个数据库里的数据
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private User creator;

    // 参与者列表（一个事件有多个参与者）
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;

    // 邀请列表（一个事件对应多个邀请）
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations;

    // ========== Getter 和 Setter 方法 ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}
