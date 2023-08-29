package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserRequestDto userEntitytoDto(User user);
	User userRequestDtoToEntity(UserRequestDto userRequestDto);
	
	
}
