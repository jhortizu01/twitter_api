package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.UserResponseDto;
import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.entities.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

	@Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User user);

	UserRequestDto userEntitytoDto(User user);

	User userRequestDtoToEntity(UserRequestDto userRequestDto);

}
