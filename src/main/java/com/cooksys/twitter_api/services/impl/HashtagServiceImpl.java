package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public List<HashtagDto> getAllHashTags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }
}
