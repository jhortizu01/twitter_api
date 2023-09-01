package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private User getUser(Credentials credentials) {
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotFoundException("Invalid credentials. Please try again.");
        }
        return optionalUser.get();
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // User user = getUser(userRequestDto.getCredentials().getUsername())
        return null;
    }

}
