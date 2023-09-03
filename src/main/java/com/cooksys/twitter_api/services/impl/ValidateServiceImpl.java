package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public boolean doesUsernameExist(String username) {
        Optional<User> user = userRepository.findByCredentialsUsername(username);

        if (user.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean validateUsername(String username) {

        Optional<User> userToFind = userRepository.findByCredentialsUsername(username);

        return userToFind.isEmpty();


    }

    @Override
    public Boolean verifyHashtag(String label) {
        Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);
        return hashtag.isPresent();
    }

}
