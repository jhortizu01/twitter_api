package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponseDto getSpecificUser(String username) {
        List<User> user = userRepository.findByCredentialsUsername(username);
        if(user.isEmpty()) {
            throw new NotFoundException("No user with username: " + username);
        }

        return userMapper.entityToDto(user.get(0));
    }
}
