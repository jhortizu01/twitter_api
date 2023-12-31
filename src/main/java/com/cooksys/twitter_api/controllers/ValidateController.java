package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;
    private final UserRepository userRepository;

    @GetMapping("/username/available/@{username}")
    public boolean validateUsername(@PathVariable String username) {
        return validateService.validateUsername(username);

    }

    @GetMapping("/tag/exists/{label}")
    public Boolean verifyHashtag(@PathVariable String label) {
        return validateService.verifyHashtag(label);
    }


    @GetMapping("/username/exists/@{username}")
    public boolean doesUsernameExist(@PathVariable String username) {
        return validateService.doesUsernameExist(username);
    }

}
