package com.cooksys.twitter_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name="tweet")
@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    private Timestamp posted;

    private boolean deleted;

    private String content;

}
