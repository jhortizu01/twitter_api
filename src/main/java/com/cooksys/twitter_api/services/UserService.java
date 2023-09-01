package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

	List<UserResponseDto> getAllActiveUsers();

	boolean validateUsername(String username);
	
	void unfollow(CredentialsDto username);

	void follow(CredentialsDto username);

	List<TweetResponseDto> getFeed(String username);
	
	List<TweetResponseDto> getMentions(Long id);

	UserResponseDto createUser(UserRequestDto userRequestDto);

}
