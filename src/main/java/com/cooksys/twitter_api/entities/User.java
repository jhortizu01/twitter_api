package com.cooksys.twitter_api.entities;

import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.entities.embeddable.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
@Table(name = "user_table")
public class User {

	@Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials credentials;

    @Embedded
    private Profile profile;
    
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp joined;

    private boolean deleted = false;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> likedTweets = new ArrayList<>();

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentionedTweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "followers_following")
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

}