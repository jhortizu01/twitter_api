package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface UserService {
    UserResponseDto getSpecificUser(String username);
}
