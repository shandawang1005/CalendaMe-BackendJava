package com.calendame.backend.models;

import jakarta.persistence.*;
import java.time.Duration;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "invitations")
public class Invitation {

    // 主键 ID，自增长
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联的 Event ID，不能为空
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    // 邀请者的 User ID，不能为空
    @Column(name = "inviter_id", nullable = false)
    private Long inviterId;

    // 被邀请者的 User ID，不能为空
    @Column(name = "invitee_id", nullable = false)
    private Long inviteeId;

    // 邀请状态，默认为 pending
    @Column(length = 20)
    private String status = "pending";

    // ========== 关系字段 ==========

    // 关联 Event
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private Event event;

    // 关联邀请者 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", insertable = false, updatable = false)
    private User inviter;

    // 关联被邀请者 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", insertable = false, updatable = false)
    private User invitee;

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

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(Long inviteeId) {
        this.inviteeId = inviteeId;
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

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public User getInvitee() {
        return invitee;
    }

    public void setInvitee(User invitee) {
        this.invitee = invitee;
    }

    // ========== 辅助方法 ==========

    public String getEventTitle() {
        return event != null ? event.getTitle() : null;
    }

    public String getFormattedStartTime() {
        return event != null && event.getStartTime() != null ? event.getStartTime().toString() : null;
    }

    public String getFormattedEndTime() {
        return event != null && event.getEndTime() != null ? event.getEndTime().toString() : null;
    }

    public Long getEventDurationInMinutes() {
        if (event != null && event.getStartTime() != null && event.getEndTime() != null) {
            return Duration.between(event.getStartTime(), event.getEndTime()).toMinutes();
        }
        return null;
    }

    public String getInviterUsername() {
        return inviter != null ? inviter.getUsername() : null;
    }

    public String getInviteeUsername() {
        return invitee != null ? invitee.getUsername() : null;
    }
}
