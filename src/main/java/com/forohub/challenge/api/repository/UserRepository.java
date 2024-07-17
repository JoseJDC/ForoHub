package com.forohub.challenge.api.repository;

import com.forohub.challenge.api.models.user.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {

    UserDetails findByLogin(String subject);

    boolean existsByLogin(String login);

}
