package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;

    @Override
    public Tweet getTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty() || optionalTweet.get().isDeleted()) {
            throw new NotFoundException("No tweet with id: " + id);
        }
        return optionalTweet.get();
    }

    @Override
    public List<TweetResponseDto> getAllActiveTweets() {
        var result = tweetRepository.findAll().stream()
                .filter(tweet -> !tweet.isDeleted()).toList();
        return tweetMapper.entitiesToDtos(result);
    }

    @Override
    public TweetResponseDto getTweetById(Long id) {
        return tweetMapper.tweetToDto(getTweet(id));
    }

}
