package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {

    List<HashtagDto> getAllHashTags();

    List<TweetResponseDto> getTags(String label);

}
