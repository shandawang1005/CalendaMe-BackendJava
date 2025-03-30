package com.calendame.backend.seeders;

import com.calendame.backend.models.Event;
import com.calendame.backend.models.Friend;
import com.calendame.backend.models.Participant;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.EventRepository;
import com.calendame.backend.repositories.FriendRepository;
import com.calendame.backend.repositories.ParticipantRepository;
import com.calendame.backend.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(4) // ✅ 排在第四个执行：先 user → friend → event → participant
public class ParticipantSeeder implements CommandLineRunner {

    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final FriendRepository friendRepository;

    public ParticipantSeeder(
        ParticipantRepository participantRepository,
        UserRepository userRepository,
        EventRepository eventRepository,
        FriendRepository friendRepository
    ) {
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 如果已有参与者数据就跳过
        if (participantRepository.count() > 0) return;

        // 查找已存在的用户和事件
        Optional<Event> meetingOpt = eventRepository.findById(1L);
        Optional<Event> lunchOpt = eventRepository.findById(2L);
        Optional<User> demoOpt = userRepository.findById(1L);
        Optional<User> lydiaOpt = userRepository.findById(4L);

        if (meetingOpt.isEmpty() || lunchOpt.isEmpty() || demoOpt.isEmpty() || lydiaOpt.isEmpty()) {
            System.out.println("⚠️ Required users or events not found.");
            return;
        }

        Event meeting = meetingOpt.get();
        Event lunch = lunchOpt.get();
        User demo = demoOpt.get();
        User lydia = lydiaOpt.get();

        List<Participant> participants = new ArrayList<>();

        // ✅ 注意：必须显式设置 eventId 和 userId
        if (areFriends(demo, meeting.getCreator())) {
            Participant p1 = new Participant();
            p1.setEventId(meeting.getId());
            p1.setUserId(demo.getId());
            p1.setEvent(meeting);
            p1.setUser(demo);
            p1.setStatus("pending");
            participants.add(p1);
        }

        if (areFriends(lydia, lunch.getCreator())) {
            Participant p2 = new Participant();
            p2.setEventId(lunch.getId());
            p2.setUserId(lydia.getId());
            p2.setEvent(lunch);
            p2.setUser(lydia);
            p2.setStatus("pending");
            participants.add(p2);
        }

        participantRepository.saveAll(participants);
        System.out.println("✅ Seeded participants into PostgreSQL.");
    }

    // ✅ 双向判断是否是好友（通过 Friend 表）
    private boolean areFriends(User a, User b) {
        return friendRepository.existsByUserAndFriendAndAcceptedTrue(a, b)
            || friendRepository.existsByUserAndFriendAndAcceptedTrue(b, a);
    }
}
