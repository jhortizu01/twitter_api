package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

}
