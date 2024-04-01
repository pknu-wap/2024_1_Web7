package com.dogventure.dogweb.mainLogic.repository;

import com.dogventure.dogweb.mainLogic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByOauth2Key(String oauth2Key);
}
