package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;

import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;

import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.HashtagMapper;
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

    private final HashtagMapper hashtagMapper;

    private User getUser(Credentials credentials) {
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotFoundException("Invalid credentials. Please try again.");
        }
        return optionalUser.get();
    }

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
    
    

    @Override
    public TweetResponseDto deleteTweetById(Long id, Credentials credentials) {
        Tweet tweet = getTweet(id);
        User user = getUser(credentials);
        if (user != tweet.getAuthor())
            throw new NotAuthorizedException("Tweet has not been deleted. Entered credentials do not match tweet author.");
        tweet.setDeleted(true);
        return tweetMapper.tweetToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public void addLikeToTweet(Long id, Credentials credentials) {
        Tweet tweet = getTweet(id);
        User user = getUser(credentials);
        if (!user.getLikedTweets().contains(tweet)) {
            user.getLikedTweets().add(tweet);
            tweet.getLikedByUsers().add(user);
            userRepository.saveAndFlush(user);
        } else {
            throw new Error("You can only like a tweet once.");
        }
    }

    @Override
    public List<HashtagDto> getTweetByTags(Long id) {
        return hashtagMapper.entitiesToDtos(getTweet(id).getHashtags());
    }

}
