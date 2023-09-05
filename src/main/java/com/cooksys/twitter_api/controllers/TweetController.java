package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllActiveTweets() {
        return tweetService.getAllActiveTweets();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto replyTweetById(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.replyTweetById(id, tweetRequestDto);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplyToTweetById(@PathVariable Long id) {
        return tweetService.getRepliesToTweetById(id);
    }
    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Long id, @RequestBody Credentials credentials) {
        return tweetService.deleteTweetById(id, credentials);
    }

    @PostMapping("/{id}/like")
    public void addLikeToTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
        tweetService.addLikeToTweet(id, credentials);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getTweetLikes(@PathVariable Long id) {
        return tweetService.getTweetLikes(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetByTags(@PathVariable Long id) {
        return tweetService.getTweetByTags(id);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.repostTweet(id, credentialsDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

}