package org.ideacloud.repositories;

import org.ideacloud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findAllByOrderByUserIdDesc();

    List<User> findAllByUserIdIn(List<Long> userIds);
}
