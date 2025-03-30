package com.calendame.backend.models;

import jakarta.persistence.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "participants", uniqueConstraints = {
        // @UniqueConstraint 对应 __table_args__ 中的联合唯一键 (user_id, event_id)
        @UniqueConstraint(columnNames = { "user_id", "event_id" }, name = "unique_user_event")
})
public class Participant implements Serializable {

    // 主键 ID，自增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联的 Event 的 ID，不能为空
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    // 关联的 User 的 ID，不能为空
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 参与状态，比如 'pending', 'accepted', 'declined'，不能为空
    @Column(nullable = false, length = 20)
    private String status;

    // ========== 关系字段 ==========

    // 与 Event 的多对一关系，指定了 event_id 是外键
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private Event event;

    // 与 User 的多对一关系，指定了 user_id 是外键
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // ========== Getter 和 Setter ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
