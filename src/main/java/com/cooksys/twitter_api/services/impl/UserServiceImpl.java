package com.cooksys.twitter_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
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
	private final CredentialsMapper credentialsMapper;

	@Override
	public List<UserResponseDto> getAllActiveUsers() {
		List<User> result = userRepository.findByDeletedIsFalse();
		//findAll().stream().filter(user -> !user.isDeleted()).toList();
		if(result.isEmpty()) {
			List<User> emptyList = new ArrayList<>();
			return userMapper.entitiesToDtos(emptyList);
		}
		
		return userMapper.entitiesToDtos(result);
	}

    @Override
    public List<TweetResponseDto> getFeed(String username) {

		Optional<User> currentUser = userRepository.findByCredentialsUsername(username);
		if (currentUser.isEmpty()) {
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
	public void unfollow(CredentialsDto credentials, String username) {

		Credentials creds = credentialsMapper.dtoToEntity(credentials);

		User currentUser = userRepository.findByCredentials(creds).get();

		User userToUnfollow = userRepository.findByCredentialsUsername(username).get();

		currentUser.getFollowing().remove(userToUnfollow);

		userRepository.saveAndFlush(currentUser);

	}

	@Override
	public void follow(CredentialsDto credentials, String username) {
		Credentials creds = credentialsMapper.dtoToEntity(credentials);

		User currentUser = userRepository.findByCredentials(creds).get();

		User userToFollow = userRepository.findByCredentialsUsername(username).get();

		currentUser.getFollowing().add(userToFollow);

		userRepository.saveAndFlush(currentUser);

	}

	@Override
	public List<TweetResponseDto> getMentions(Long id) {
		User currentUser = userRepository.findById(id).get();

		List<Tweet> mentions = currentUser.getMentionedTweets();

		return tweetMapper.entitiesToDtos(mentions);
	}

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = getUserByUsername(userRequestDto.getCredentials().getUsername());
        if (user == null) {
            user = userMapper.userRequestDtoToEntity(userRequestDto);
        } else if (user.isDeleted()) {
            user.setCredentials(user.getCredentials());
            user.setProfile(user.getProfile());
            user.setDeleted(false);
        } else
            throw new BadRequestException("Username must be unique");
        validateUser(user);
        return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        return optionalUser.orElse(null);
    }

    private void validateUser(User user) {
        if (user == null)
            throw new BadRequestException("No credentials were provided. Please try again.");

        if (user.getCredentials() == null)
            throw new BadRequestException("No credentials were provided. Please try again.");

        if (user.getProfile() == null)
            throw new BadRequestException("No profile info was provided. Please try again.");

        if (user.getCredentials().getUsername() == null || user.getCredentials().getPassword() == null)
            throw new BadRequestException("Missing credentials. Please check the entered username and password.");

        if (user.getProfile().getEmail() == null)
            throw new BadRequestException("Missing profile info. Please check the entered info and try again.");
    }

}
