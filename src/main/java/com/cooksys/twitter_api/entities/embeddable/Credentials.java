package com.cooksys.twitter_api.entities.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Entity
@Embeddable
@NoArgsConstructor
@Data
public class Credentials {

    private String username;

    private String password;

}
