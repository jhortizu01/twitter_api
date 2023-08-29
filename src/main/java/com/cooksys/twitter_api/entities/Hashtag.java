package com.cooksys.twitter_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name="hashtag")
@Entity
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;

    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

}
