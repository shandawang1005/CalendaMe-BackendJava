package com.calendame.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendame.backend.models.SharedFile;

@Repository
public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {

}
