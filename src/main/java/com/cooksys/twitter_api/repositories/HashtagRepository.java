package com.cooksys.twitter_api.repositories;

import com.cooksys.twitter_api.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    // TODO: Do you need any derived queries? If so add them here.

    Optional<Hashtag> findByLabel(String label);

}
