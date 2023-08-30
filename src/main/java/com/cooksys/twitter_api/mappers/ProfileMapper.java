package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.entities.embeddable.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

	ProfileDto profileEntityToDto(Profile profile);

	Profile profileDtoToEntity(ProfileDto profileDto);

}
