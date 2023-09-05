package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<HashtagDto> getAllHashTags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTags(String label) {

        List<Tweet> allTweets = tweetRepository.findAll();

        List<TweetResponseDto> returnList = new ArrayList<>();

        for (Tweet t : allTweets) {
            if (!(t.isDeleted()) && t.getContent().contains("#" + label)) {
                returnList.add(tweetMapper.tweetToDto(t));
            }
        }

        if (returnList.isEmpty()) {
            throw new NotFoundException("No tweets with applicable Tag");
        }

        return returnList;
    }

}
