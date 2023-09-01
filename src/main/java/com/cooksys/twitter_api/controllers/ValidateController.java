package com.cooksys.twitter_api.controllers;

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

    private ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public Boolean verifyHashtag(@PathVariable String label) {
        return validateService.verifyHashtag(label);
    }

}
