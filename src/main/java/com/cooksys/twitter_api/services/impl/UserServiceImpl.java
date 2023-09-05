package com.cooksys.twitter_api.services.impl;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;

    @Override
    public UserResponseDto getSpecificUser(String username) {
        Optional<User> user = userRepository.findByCredentialsUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        return userMapper.entitytoDto(user.get());
    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        Optional<User> user = userRepository.findByCredentialsUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        return tweetMapper.entitiesToDtos(user.get().getTweets());
    }

    @Override
    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {

        Optional<User> user = userRepository.findByCredentialsUsername(username);

        if (user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        User userEntity = userMapper.userRequestDtoToEntity(userRequestDto);

        user.get().setCredentials(userEntity.getCredentials());
        user.get().setProfile(userEntity.getProfile());
        return userMapper.entitytoDto(userRepository.saveAndFlush(user.get()));
    }

    @Override
    public List<UserResponseDto> getAllActiveUsers() {
        List<User> result = userRepository.findByDeletedIsFalse();
        if (result.isEmpty()) {
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
    public List<UserResponseDto> getFollowers(String username) {
        User user = userRepository.findByCredentialsUsername(username).get();
        return userMapper.entitiesToDtos(user.getFollowers());
    }

    @Override
    public List<UserResponseDto> getFollowedUsers(String username) {
        User user = userRepository.findByCredentialsUsername(username).get();
        return userMapper.entitiesToDtos(user.getFollowing());
    }

    @Override
    public List<TweetResponseDto> getMentions(Long id) {
        User currentUser = userRepository.findById(id).get();

        List<Tweet> mentions = currentUser.getMentionedTweets();

        return tweetMapper.entitiesToDtos(mentions);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
    	
    	if(userRequestDto.getCredentials() == null || userRequestDto.getProfile() == null || userRequestDto.getCredentials().getUsername() == null || userRequestDto.getCredentials().getPassword() == null || userRequestDto.getProfile().getEmail() == null) {
    	    throw new BadRequestException("Necessary fields must not be empty");
    	}
    	
    	Optional<User> user = userRepository.findByUsername(userRequestDto.getCredentials().getUsername());
        //getUserByUsername(userRequestDto.getCredentials().getUsername());
        if(user.isPresent() && !user.get().isDeleted()) {
        	
        	throw new BadRequestException("User is already present in database");
        }
        
        if(user.isPresent()) {
        	
        	user.get().setDeleted(false);
        	
			return userMapper.entitytoDto(userRepository.saveAndFlush(user.get()));

        }
        
        User newUser = userMapper.userRequestDtoToEntity(userRequestDto);
        
        return userMapper.entitytoDto(userRepository.saveAndFlush(newUser));
        
        
        
//        if (	 .isEmpty()){
//            user = userMapper.userRequestDtoToEntity(userRequestDto);
//        } else if (user.get().isDeleted()) {
//            user.get().setCredentials(user.get().getCredentials());
//            user.setProfile(user.getProfile());
//            user.setDeleted(false);
//        } else
//            throw new BadRequestException("Username must be unique");
//        validateUser(user);
//        return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto deleteUser(String username, CredentialsDto credentials) {
        Credentials creds = credentialsMapper.dtoToEntity(credentials);
        User toDelete = userRepository.findByCredentials(creds).get();
        toDelete.setDeleted(true);
        return userMapper.entitytoDto(userRepository.saveAndFlush(toDelete));
    }

    private Optional<User> getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        return optionalUser;
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
