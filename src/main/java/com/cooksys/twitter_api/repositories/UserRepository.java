package com.cooksys.twitter_api.repositories;

import com.cooksys.twitter_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCredentialsUsername(String username);
}
