package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.services.TweetService;
import com.cooksys.twitter_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TweetService tweetService;

    @GetMapping("/@{username}")
    public UserResponseDto getSpecificUser(@PathVariable String username) {
        return userService.getSpecificUser(username);
    }

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getUsersTweets(@PathVariable String username) {
        return userService.getUserTweets(username);
    }

    @PatchMapping("/@{username}")
    public UserResponseDto updateUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(username, userRequestDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllActiveUsers() {
        return userService.getAllActiveUsers();
    }

    @PostMapping("/@{username}/unfollow")
    public void unfollow(@RequestBody CredentialsDto credentials, @PathVariable String user) {
        userService.unfollow(credentials, user);
    }


    @PostMapping("/@{username}/follow")
    public void follow(@RequestBody CredentialsDto credentials, @PathVariable String username) {
        userService.follow(credentials, username);
    }


    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getFeed(String username) {
        return userService.getFeed(username);

    }

    @GetMapping("/{id}/mentions")
    public List<TweetResponseDto> getMentions(Long id) {

        return userService.getMentions(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getUsernameMentions(@PathVariable String username) {
        return tweetService.getUsernameMentions(username);
    }

}
