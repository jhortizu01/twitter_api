package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
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
    private final UserRepository userRepository;

    private User getUser(Credentials credentials) {
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotFoundException("Invalid credentials. Please try again.");
        }
        return optionalUser.get();
    }

    @Override
    public Tweet getTweet(Long id) {
        Optional<Tweet> optionalUser = tweetRepository.findById(id);
        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotFoundException("No tweet with id: " + id);
        }
        return optionalUser.get();
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

    @Override
    public TweetResponseDto deleteTweetById(Long id, Credentials credentials) {
        Tweet tweet = getTweet(id);
        User user = getUser(credentials);
        if (user != tweet.getAuthor())
            throw new NotAuthorizedException("Tweet has not been deleted. Entered credentials do not match tweet author.");
        tweet.setDeleted(true);
        return tweetMapper.tweetToDto(tweetRepository.saveAndFlush(tweet));
    }

}
