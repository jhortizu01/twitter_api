package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto getSpecificUser(String username);

    List<TweetResponseDto> getUserTweets(String username);

    UserResponseDto updateUser(String username, UserRequestDto userRequestDto);

    List<UserResponseDto> getAllActiveUsers();

    void unfollow(CredentialsDto credentials, String username);

    void follow(CredentialsDto credentials, String username);

    List<TweetResponseDto> getFeed(String username);

    List<TweetResponseDto> getMentions(Long id);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getFollowers(String username);

    List<UserResponseDto> getFollowedUsers(String username);

    UserResponseDto deleteUser(String username, CredentialsDto credentials);
}
