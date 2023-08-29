package com.cooksys.twitter_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "tweet")
@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    private Timestamp posted;

    private boolean deleted;

    private String content;

    @ManyToMany
    @JoinTable(name = "tweet_hashtags",
            joinColumns = {@JoinColumn(name = "tweet_id")},
            inverseJoinColumns = {@JoinColumn(name = "hashtag_id")}
    )
    private List<Hashtag> hashtags;

    // Join Table Reference Site
    // https://www.baeldung.com/jpa-many-to-many

}
