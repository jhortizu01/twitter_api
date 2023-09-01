package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public boolean validateUsername(String username) {
		
		Optional<User> userToFind = userRepository.findByCredentialsUsername(username);
		
		if(userToFind.isEmpty()) {
			return true;
		}else {
			return false;
		}

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

	// TODO: User is being created but variable 'phoneNumber' is not populating

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User user = getUserByUsername(userRequestDto.getCredentials().getUsername());
		if (user == null) {
			user = userMapper.userRequestDtoToEntity(userRequestDto);
		} else if (user.isDeleted()) {
			user.setCredentials(user.getCredentials());
			user.setProfile(user.getProfile());
			user.setDeleted(false);
		}
		else
			throw new BadRequestException("Username must be unique");
		validateUser(user);
		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

	private User getUserByUsername(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        return optionalUser.orElse(null);
    }

	private void validateUser(User user) {
		if (user.getCredentials().getUsername() == null)
			throw new BadRequestException("username is required.");
		if (user.getCredentials().getPassword() == null)
			throw new BadRequestException("password is required.");
		if (user.getProfile().getEmail() == null)
			throw new BadRequestException("email is required.");
	}

}
