package com.cooksys.twitter_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagResponseDto entityToDto(Hashtag entity);
	List<HashtagResponseDto> entitiesToDtos(List<Hashtag> entities);
	
	//Boolean dtoToBoolean(HashtagRequestDto hashtagrequestDto);
	//Hashtag dtoToEntity(HashtagRequestDto requestDto);
	
	
	
}
