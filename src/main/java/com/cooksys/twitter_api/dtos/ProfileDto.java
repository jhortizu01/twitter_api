package com.cooksys.twitter_api.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ProfileDto {

	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	private String phoneNumber;
	
	
}
