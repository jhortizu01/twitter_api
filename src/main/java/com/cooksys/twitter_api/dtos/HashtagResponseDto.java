package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagResponseDto {
	
	
	@Column(unique = true, nullable = false)
    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

}
