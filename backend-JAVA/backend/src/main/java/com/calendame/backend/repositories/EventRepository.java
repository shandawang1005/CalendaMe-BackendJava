package com.calendame.backend.repositories;

import com.calendame.backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//这里的Long是Prim Key的类型

public interface EventRepository extends JpaRepository<Event, Long> {

} 