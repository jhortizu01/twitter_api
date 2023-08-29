package com.cooksys.twitter_api.entities;

import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.entities.embeddable.Profile;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials username;

    @Embedded
    private Profile profile;


    /**
     * For followers_following table
     *  one user would have many followers & one user could be following many people
     *  many to many relationship between followers and following
     */


}
