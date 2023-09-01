package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto getSpecificUser(String username);

    List<TweetResponseDto> getUserTweets(String username);

    UserResponseDto updateUser(String username, UserRequestDto userRequestDto);
}
