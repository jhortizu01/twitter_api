package com.cooksys.twitter_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final TweetMapper tweetMapper;

	@Override
	public List<UserResponseDto> getAllActiveUsers() {
		var result = userRepository.findAll().stream().filter(user -> !user.isDeleted()).toList();
		return userMapper.entitiesToDtos(result);
	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {

		User currentUser = userRepository.findByCredentialsUsername(username);
		List<Tweet> userFeed = currentUser.getTweets();
		for (User u : currentUser.getFollowing()) {
			userFeed.addAll(u.getTweets());
		}
		return tweetMapper.entitiesToDtos(userFeed);

	}

	@Override
	public boolean validateUsername() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unfollow(CredentialsDto username) {

	}
	
	@Override
	public void follow(CredentialsDto username) {

	}
	
	@Override
	public boolean usernameExists(String username) {
		return false;

	}

	@Override
	public List<UserResponseDto> getMentions(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



}
