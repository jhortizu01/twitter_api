package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;

public interface HashtagService {

    List<HashtagDto> getAllHashTags();
    
    List<TweetResponseDto> getTags(String label);

}
