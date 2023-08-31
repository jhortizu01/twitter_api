package com.cooksys.twitter_api.services;

import com.cooksys.twitter_api.dtos.HashtagDto;

import java.util.List;

public interface HashtagService {
    List<HashtagDto> getAllHashTags();
}
