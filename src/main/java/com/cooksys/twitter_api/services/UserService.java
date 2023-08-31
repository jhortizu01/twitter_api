package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getAllActiveUsers();

	boolean validateUsername();
	
	void unfollow(CredentialsDto username);

	void follow(CredentialsDto username);

	List<UserResponseDto> getMentions(Long id);

	boolean usernameExists(String username);

	List<TweetResponseDto> getFeed(String username);

}
