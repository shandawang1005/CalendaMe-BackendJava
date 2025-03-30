package com.calendame.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calendame.backend.models.Friend;
import com.calendame.backend.models.User;
import java.util.*;

//这里的Long是Prim Key的类型
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByUserAndFriendAndAcceptedTrue(User user, User friend);

    boolean existsByUserAndFriendAndAcceptedFalse(User user, User friend);

    Optional<Friend> findByUserAndFriendAndAcceptedFalse(User user, User friend);
}
