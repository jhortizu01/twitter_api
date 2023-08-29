package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagResponseDto entityToDto(Hashtag entity);

	List<HashtagResponseDto> entitiesToDtos(List<Hashtag> entities);

}
