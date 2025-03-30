package com.calendame.backend.seeders;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.calendame.backend.models.Event;
import com.calendame.backend.repositories.EventRepository;

@Component
@Order(3)
public class EventSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;

    public EventSeeder(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 如果events已有数据则跳过
        if (eventRepository.count() > 0)
            return;
        // 创建第一个 Event（会议）
        Event event1 = new Event();
        event1.setTitle("Meeting");
        event1.setStartTime(LocalDateTime.now().plusWeeks(4));
        event1.setEndTime(LocalDateTime.now().plusWeeks(4).plusHours(3));
        event1.setLocation("Zoom");
        event1.setVisibility("private");
        event1.setRecurring(false);
        event1.setCreatorId(2L);

        // 创建第二个 Event（午餐）
        Event event2 = new Event();
        event2.setTitle("Lunch");
        event2.setStartTime(LocalDateTime.now().plusWeeks(4).plusDays(1));
        event2.setEndTime(LocalDateTime.now().plusWeeks(4).plusDays(1).plusHours(3));
        event2.setLocation("Cafe");
        event2.setVisibility("private");
        event2.setRecurring(false);
        event2.setCreatorId(2L); // ID=2 的用户

        eventRepository.saveAll(List.of(event1, event2));

        System.out.println("✅ Seeded events into PostgreSQL.");
    }
}
