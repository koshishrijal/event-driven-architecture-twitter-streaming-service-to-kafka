package com.kosh.eventDriven.config;

import org.springframework.stereotype.Component;

@Component
public class TwitterConfig {
    public String getStream_api_url() {
        return "https://api.twitter.com/2/tweets/search/stream";
    }

    public String getBearer_access_token() {
        return "*****************************";
    }
}
