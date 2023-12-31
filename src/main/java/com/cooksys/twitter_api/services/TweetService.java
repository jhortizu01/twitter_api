package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.embeddable.Credentials;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAllActiveTweets();

    Tweet getTweet(Long id);

    TweetResponseDto getTweetById(Long id);

    TweetResponseDto replyTweetById(Long id, TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getRepliesToTweetById(Long id);

    TweetResponseDto deleteTweetById(Long id, Credentials credentials);

    void addLikeToTweet(Long id, Credentials credentials);

    List<HashtagDto> getTweetByTags(Long id);

    List<UserResponseDto> getTweetLikes(Long id);

    TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto);

    List<TweetResponseDto> getUsernameMentions(String username);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getRepostOfTweetById(Long id);

    ContextDto getContextForTweet(Long id);
}
