package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@JoinTable(name = "tweet_hashtags", joinColumns = { @JoinColumn(name = "tweet_id") }, inverseJoinColumns = {
			@JoinColumn(name = "hashtag_id") })
	private List<Hashtag> hashtags;

	@ManyToOne
	private User user;
	
//	@ManyToMany
//	private List<Tweet> tweets;
	
//	@ManyToMany
//	private List<User> users;
	
//	@OneToMany
//	private List<Tweet> replyTweets;
//	
//	@OneToMany
//	private List<Tweet> tweetReposts;
	



	// Join Table Reference Site
	// https://www.baeldung.com/jpa-many-to-many

}
