package com.calendame.backend.seeders;

import com.calendame.backend.models.Event;
import com.calendame.backend.models.Invitation;
import com.calendame.backend.models.User;
import com.calendame.backend.repositories.EventRepository;
import com.calendame.backend.repositories.InvitationRepository;
import com.calendame.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class InvitationSeeder implements CommandLineRunner {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public InvitationSeeder(
            InvitationRepository invitationRepository,
            UserRepository userRepository,
            EventRepository eventRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (invitationRepository.count() > 0)
            return;

        User bobbie = userRepository.findByUsername("bobbie").orElse(null);
        User lydia = userRepository.findByUsername("lydia").orElse(null);
        Event meeting = eventRepository.findById(1L).orElse(null); // 假设 Meeting 是第一个 event
        Event lunch = eventRepository.findById(2L).orElse(null); // 假设 Lunch 是第二个 event

        if (bobbie != null && lydia != null && meeting != null && lunch != null) {
            Invitation inv1 = new Invitation();
            inv1.setInvitee(bobbie);
            inv1.setInviteeId(bobbie.getId());
            inv1.setInviterId(meeting.getCreatorId());
            inv1.setEvent(meeting);
            inv1.setEventId(meeting.getId());
            inv1.setStatus("pending");

            Invitation inv2 = new Invitation();
            inv2.setInvitee(lydia);
            inv2.setInviteeId(lydia.getId());
            inv2.setInviterId(lunch.getCreatorId());
            inv2.setEvent(lunch);
            inv2.setEventId(lunch.getId());
            inv2.setStatus("accepted");

            invitationRepository.save(inv1);
            invitationRepository.save(inv2);

            System.out.println("✅ Seeded invitations into PostgreSQL.");
        } else {
            System.out.println("⚠️ Missing required data for seeding invitations.");
        }
    }
}
