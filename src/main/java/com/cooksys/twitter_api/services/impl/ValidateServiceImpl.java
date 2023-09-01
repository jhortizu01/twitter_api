package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private HashtagRepository hashtagRepository;

    @Override
    public Boolean verifyHashtag(String label) {
        Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);
        return hashtag.isPresent();
    }

}
