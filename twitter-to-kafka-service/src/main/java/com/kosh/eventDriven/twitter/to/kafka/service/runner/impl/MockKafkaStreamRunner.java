package com.kosh.eventDriven.twitter.to.kafka.service.runner.impl;


import com.kosh.eventDriven.twitter.to.kafka.service.exception.TwitterToKafkaServiceException;
import com.kosh.eventDriven.twitter.to.kafka.service.listner.TwitterToKafkaListner;
import com.kosh.eventDriven.twitter.to.kafka.service.runner.StreamRunner;
import com.kosh.eventDriven.config.TwitterToKafkaConfigData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "true")
public class MockKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final TwitterToKafkaListner twitterToKafkaListner;

    private static final String[] WORDS = new String[]{
            "Gil Britt", "elementum@yahoo.ca", "New Zealand",
            "Nathan Floyd", "ultricies.ornare.elit@icloud.com", "Ukraine",
            "Colby George", "id.erat.etiam@google.ca", "Canada",
            "Raymond Patterson", "donec.fringilla@hotmail.edu", "Costa Rica",
            "Laith Pierce", "feugiat.tellus@protonmail.edu", "Germany"
    };

    private static final String tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\" ," +
            "\"id\":\"{1}\" ," +
            "\"text\":\"{2}\" " +
//            "\"user\":\"{3}\" " +
            "}";
    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    private static final Random RANDOM = new Random();

    public MockKafkaStreamRunner(TwitterToKafkaConfigData twitterToKafkaConfigData, TwitterToKafkaListner twitterToKafkaListner) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.twitterToKafkaListner = twitterToKafkaListner;
    }

    @Override
    public void start() throws TwitterException {
        String[] keywords = twitterToKafkaConfigData.getTwitterKeywords().toArray(new String[0]);
        int minTweetLength = twitterToKafkaConfigData.getMockMinTweetLength();
        int maxTweetLength = twitterToKafkaConfigData.getMockMaxTweetLength();
        long sleepMs = twitterToKafkaConfigData.getMockSleepMs();
        log.info("Starting mock twitter filter stream for keywords: {}", Arrays.toString(keywords));
        simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepMs);

    }

    private void simulateTwitterStream(String[] keywords, int minTweetLength, int maxTweetLength, long sleepMs) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    String formattedTweetJson = getFormattedTweet(keywords, minTweetLength, maxTweetLength);
                    Status status = TwitterObjectFactory.createStatus(formattedTweetJson);
                    twitterToKafkaListner.onStatus(status);
                    sleep(sleepMs);
                }
            } catch (TwitterException e) {
                log.error("Error generating tweet: {}", e.getMessage());
            }
        });

    }

    private void sleep(long sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new TwitterToKafkaServiceException("Error while sleeping for new status to create!");
        }
    }

    private String getFormattedTweet(String[] keywords, int minTweetLength, int maxTweetLength) {
        String[] params = new String[]{
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandonTweetContent(keywords, minTweetLength, maxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
        };
        return formatTweetAsJson(params);
    }

    private String formatTweetAsJson(String[] params) {
        String tweet = tweetAsRawJson;
        for (int i = 0; i < params.length; i++) {
            tweet = tweet.replace("{" + i + "}", params[i]);
        }
        return tweet;
    }

    private String getRandonTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweet = new StringBuilder();
        int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength;
        return constructRandomTweet(keywords, tweet, tweetLength);
    }

    private String constructRandomTweet(String[] keywords, StringBuilder tweet, int tweetLength) {
        for (int i = 0; i < tweetLength; i++) {
            tweet.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
            if (i == tweetLength / 2) {
                tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }
        return tweet.toString().trim();
    }


}
