package com.cooksys.twitter_api.services.impl;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.embeddable.Credentials;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    private final HashtagMapper hashtagMapper;
    private final UserMapper userMapper;

    private User getUser(Credentials credentials) {
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotFoundException("Invalid credentials. Please try again.");
        }
        return optionalUser.get();
    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        return optionalUser.orElse(null);
    }

    @Override
    public Tweet getTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty() || optionalTweet.get().isDeleted()) {
            throw new NotFoundException("No tweet with id: " + id);
        }
        return optionalTweet.get();
    }

    @Override
    public List<TweetResponseDto> getAllActiveTweets() {
        var result = tweetRepository.findAll().stream()
                .filter(tweet -> !tweet.isDeleted()).toList();
        return tweetMapper.entitiesToDtos(result);
    }

    @Override
    public TweetResponseDto getTweetById(Long id) {
        return tweetMapper.tweetToDto(getTweet(id));
    }

    @Override
    public TweetResponseDto replyTweetById(Long id, TweetRequestDto tweetRequestDto) {
        Tweet tweetToReplyTo = getTweet(id);
        User user = getUser(tweetRequestDto.getCredentials());
        Tweet tweet = new Tweet();
        tweet.setInReplyTo(tweetToReplyTo);
        tweet.setContent(tweetRequestDto.getContent());
        tweet.setAuthor(user);
        parseForUserMentions(tweet);
        parseForHashtags(tweet);
        return tweetMapper.tweetToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public List<TweetResponseDto> getRepliesToTweetById(Long id) {
        Tweet tweet = getTweet(id);
        return tweetMapper.entitiesToDtos(tweet.getReplies());
    }


    @Override
    public TweetResponseDto deleteTweetById(Long id, Credentials credentials) {
        Tweet tweet = getTweet(id);
        User user = getUser(credentials);
        if (user != tweet.getAuthor())
            throw new NotAuthorizedException("Tweet has not been deleted. Entered credentials do not match tweet author.");
        tweet.setDeleted(true);
        return tweetMapper.tweetToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public void addLikeToTweet(Long id, Credentials credentials) {
        Tweet tweet = getTweet(id);
        User user = getUser(credentials);
        if (!user.getLikedTweets().contains(tweet)) {
            user.getLikedTweets().add(tweet);
            tweet.getLikedByUsers().add(user);
            userRepository.saveAndFlush(user);
        } else {
            throw new Error("You can only like a tweet once.");
        }
    }

    @Override
    public List<HashtagDto> getTweetByTags(Long id) {
        if (getTweet(id) == null) {
            throw new Error("Tweet does not exist, try again");
        }
        return hashtagMapper.entitiesToDtos(getTweet(id).getHashtags());
    }

    @Override
    public List<UserResponseDto> getTweetLikes(Long id) {
        if (getTweet(id) == null) {
            throw new Error("Tweet does not exist, try again");
        }
        return userMapper.entitiesToDtos(getTweet(id).getLikedByUsers());
    }

    @Override
    public TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto) {
        Tweet tweet = getTweet(id);
        Optional<User> userList = userRepository.findByCredentialsUsername(credentialsDto.getUsername());

        if (tweet == null | userList.isEmpty()) {
            throw new NotFoundException("Invalid credentials or tweet id. Please try again.");
        }

        User user = userList.get();
        tweet.setAuthor(user);
        tweetRepository.saveAndFlush(tweet);

        return tweetMapper.tweetToDto(tweet);
    }

    @Override
    public List<TweetResponseDto> getRepostOfTweetById(Long id) {
        Tweet tweet = getTweet(id);
        return tweetMapper.entitiesToDtos(tweet.getReposts());
    }

    // TODO: Add code to sort response in reverse chronological order
    @Override
    public List<TweetResponseDto> getUsernameMentions(String username) {
        User user = getUserByUsername(username);
        if (user == null || user.isDeleted())
            throw new NotFoundException("Invalid. Please try again.");

        List<Tweet> mentions = user.getMentionedTweets().stream().filter(tweet -> !tweet.isDeleted()).toList();
        return tweetMapper.entitiesToDtos(mentions);
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        User user = getUser(tweetRequestDto.getCredentials());
        Tweet tweet = new Tweet();
        tweet.setAuthor(user);
        tweet.setContent(tweetRequestDto.getContent());
        parseForUserMentions(tweet);
        parseForHashtags(tweet);
        return tweetMapper.tweetToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public ContextDto getContextForTweet(Long id) {
        Tweet tweet = getTweet(id);
        ContextDto responseDto = new ContextDto();

        List<Tweet> tweetsBefore = new ArrayList<>();
        Tweet actualTweet = tweet;
        while (actualTweet.getInReplyTo() != null) {
            actualTweet = actualTweet.getInReplyTo();
            if (!actualTweet.isDeleted())
                tweetsBefore.add(actualTweet);
        }

        List<Tweet> tweetsAfter = new ArrayList<>();
        Queue<Tweet> tweetQueue = new LinkedList<>();
        tweetQueue.addAll(tweet.getReplies());
        while (tweetQueue.size() > 0) {
            Tweet tweetActual = tweetQueue.poll();
            if (!tweetActual.isDeleted())
                tweetsAfter.add(tweetActual);
            tweetQueue.addAll(tweetActual.getReplies());
        }

        responseDto.setTarget(tweetMapper.tweetToDto(tweet));
        responseDto.setBefore(tweetMapper.entitiesToDtos(tweetsBefore));
        responseDto.setAfter(tweetMapper.entitiesToDtos(tweetsAfter));
        return responseDto;
    }

    public void parseForHashtags(Tweet tweet) {
        Pattern pattern = Pattern.compile("@\\w+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tweet.getContent());

        List<Hashtag> hashtags = new ArrayList<>();

        matcher.results().forEach(matchResult -> {
            String hashtag = matchResult.group().substring(1);
            hashtagRepository.findAll()
                    .stream()
                    .filter(hashtag1 -> hashtag1.getLabel().equals(hashtag))
                    .forEach(hashtag1 -> {
                        hashtags.add(hashtag1);
                        hashtag1.getTweets().add(tweet);
                    });
        });

        tweet.getHashtags().addAll(hashtags);
        tweetRepository.saveAndFlush(tweet);
        hashtagRepository.saveAllAndFlush(hashtags);
    }

    public void parseForUserMentions(Tweet tweet) {
        Pattern pattern = Pattern.compile("@\\w+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tweet.getContent());

        List<User> mentionedUsers = new ArrayList<>();

        matcher.results().forEach(matchResult -> {
            String username = matchResult.group().substring(1);
            userRepository.findAll()
                    .stream()
                    .filter(user -> user.getCredentials().getUsername().equals(username))
                    .forEach(user -> {
                        mentionedUsers.add(user);
                        user.getMentionedTweets().add(tweet);
                    });
        });

        tweet.getMentionedUsers().addAll(mentionedUsers);
        tweetRepository.saveAndFlush(tweet);
        userRepository.saveAllAndFlush(mentionedUsers);
    }

}
