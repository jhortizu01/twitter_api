package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDto entityToDto(Hashtag entity);

    List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

}
