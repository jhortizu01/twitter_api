package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.entities.embeddable.Profile;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Credentials username;

	@Embedded
	private Profile profile;

	@CreationTimestamp
	private Timestamp joined;

	private boolean deleted;

//	@OneToMany
//	private List<Tweet> tweets;

	@ManyToMany
	@JoinTable(name = "followers_following", joinColumns =  @JoinColumn(name = "follower_id" ),
		inverseJoinColumns = @JoinColumn(name = "following_id") )
	private List<User> users;

	@ManyToMany
	@JoinTable(name = "user_likes", joinColumns =  @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "tweet_id") )
	private List<Tweet> likedTweets;
	
	@ManyToMany
	@JoinTable(name = "user_mentions", joinColumns =  @JoinColumn(name = "user_id") , inverseJoinColumns = 
			@JoinColumn(name = "tweet_id") )
	private List<User> mentionedUsers;

	/**
	 * For followers_following table one user would have many followers & one user
	 * could be following many people many to many relationship between followers
	 * and following
	 */

}
