package com.cooksys.twitter_api.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.embeddable.Credentials;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsername(String username);

    Optional<User> findByCredentials(Credentials credentials);

    List<User> findByDeletedIsFalse();
    
    @Query(value = "SELECT * FROM user_table WHERE username=?1", nativeQuery = true)
    Optional<User> findByUsername(String username);

}