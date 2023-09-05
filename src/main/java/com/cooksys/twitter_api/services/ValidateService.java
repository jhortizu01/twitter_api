package com.cooksys.twitter_api.services;

public interface ValidateService {

    boolean doesUsernameExist(String username);

    boolean validateUsername(String username);

    Boolean verifyHashtag(String label);

}
