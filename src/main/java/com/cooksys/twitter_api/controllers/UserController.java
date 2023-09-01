package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("validate/username/available/@{username}")
	public boolean validateUsername(@PathVariable String username) {
		return userService.validateUsername(username);
		
	}
		
	
	@PostMapping("users/@{username}/unfollow")
	public void unfollow(@PathVariable String user) {
	 return ;
	}
	
	
	@PostMapping("users/@{username}/follow")
	public void follow(@PathVariable String username) {
		
	}
	
	
	@GetMapping("users/@{username}/feed")
	public List<TweetResponseDto> getFeed(String username) {
		return userService.getFeed(username);

	}
	
	@GetMapping("{id}/mentions")
	public List<TweetResponseDto> getMentions(Long id){
		
		return userService.getMentions(id);
	}

}
