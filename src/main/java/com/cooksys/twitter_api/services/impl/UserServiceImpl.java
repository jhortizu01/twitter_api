package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.UserRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;

    @Override
    public UserResponseDto getSpecificUser(String username) {
        List<User> user = userRepository.findByCredentialsUsername(username);
        if(user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        return userMapper.entityToDto(user.get(0));
    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        List<User> user = userRepository.findByCredentialsUsername(username);
        if(user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        return tweetMapper.entitiesToDtos(user.get(0).getTweets());
    }

    @Override
    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {

        List<User> user = userRepository.findByCredentialsUsername(username);

        if(user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        User userEntity = userMapper.userRequestDtoToEntity(userRequestDto);

        user.get(0).setCredentials(userEntity.getCredentials());
        user.get(0).setProfile(userEntity.getProfile());
        return userMapper.entityToDto(userRepository.saveAndFlush(user.get(0)));
    }
	

	@Override
	public List<UserResponseDto> getAllActiveUsers() {
		var result = userRepository.findAll().stream().filter(user -> !user.isDeleted()).toList();
		return userMapper.entitiesToDtos(result);
	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {

		Optional<User> currentUser = userRepository.findByCredentialsUsername(username);
		if(currentUser.isEmpty()) {
			throw new NotFoundException("User does not exist");
		}
		
		User user = currentUser.get();
		List<Tweet> userFeed = new ArrayList<>(user.getTweets());
		for (User u : user.getFollowing()) {
			userFeed.addAll(u.getTweets());
		}
		return tweetMapper.entitiesToDtos(userFeed);

	}
  

	@Override
	public void unfollow(CredentialsDto username) {

		
		
	}
	
	@Override
	public void follow(CredentialsDto username) {

		
		
	}
	

	@Override
	public List<TweetResponseDto> getMentions(Long id) {
		User currentUser = userRepository.findById(id).get();
		
		List<Tweet> mentions = currentUser.getMentionedTweets();
		
		return tweetMapper.entitiesToDtos(mentions);
	}

}
