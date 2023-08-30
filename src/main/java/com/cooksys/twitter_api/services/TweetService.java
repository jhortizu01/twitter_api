package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAllActiveTweets();

}
