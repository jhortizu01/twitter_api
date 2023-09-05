package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.entities.embeddable.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto profileEntityToDto(Profile profile);

    Profile profileDtoToEntity(ProfileDto profileDto);

}
