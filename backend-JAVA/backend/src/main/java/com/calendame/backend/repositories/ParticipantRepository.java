package com.calendame.backend.repositories;

import com.calendame.backend.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    // 目前暂时不需要自定义方法
}
