package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.entities.embeddable.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials credentials);

    Credentials dtoToEntity(CredentialsDto credentials);
}
