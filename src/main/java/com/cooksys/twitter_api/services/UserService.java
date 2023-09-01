package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getAllActiveUsers();

	boolean validateUsername(String username);
	
	void unfollow(CredentialsDto username);

	void follow(CredentialsDto username);

	List<TweetResponseDto> getFeed(String username);
	
	List<TweetResponseDto> getMentions(Long id);

}
