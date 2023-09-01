package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
    private final UserRepository userRepository;

    @Override
    public boolean doesUsernameExist(String username) {
        List<User> user = userRepository.findByCredentialsUsername(username);

        if(user.isEmpty()) {
            return false;
        } else {
            return true;
        }


    }
}
