package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

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
	public List<TweetResponseDto> getMentions(Long id){
		
		return userService.getMentions(id);
	}
}
