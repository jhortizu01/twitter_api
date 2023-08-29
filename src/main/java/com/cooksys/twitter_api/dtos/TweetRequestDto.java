package com.cooksys.twitter_api.dtos;

import com.cooksys.twitter_api.entities.embeddable.Credentials;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {
	

   private String content;
   
   private Credentials credentials;

}
