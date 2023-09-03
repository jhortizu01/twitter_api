package com.cooksys.twitter_api.services.impl;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.ValidateService;

import lombok.RequiredArgsConstructor;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.repositories.HashtagRepository;



@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final UserRepository userRepository;
	
	@Override
	public boolean validateUsername(String username) {
		
		Optional<User> userToFind = userRepository.findByCredentialsUsername(username);
		
		return userToFind.isEmpty();
		
		
	}
	
	

    private HashtagRepository hashtagRepository;

    @Override
    public Boolean verifyHashtag(String label) {
        Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);
        return hashtag.isPresent();
    }

}
