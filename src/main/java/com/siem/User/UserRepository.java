package com.siem.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUserNameContainingIgnoreCase(String userName);

    Optional<User> findByUserName(String userName);

}