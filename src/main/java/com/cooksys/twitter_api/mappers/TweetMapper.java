package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetResponseDto tweetToDto(Tweet entity);

	Tweet entityToTweet(TweetRequestDto tweetRequestDto);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

}
