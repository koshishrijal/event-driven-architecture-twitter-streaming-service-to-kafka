package com.kosh.eventDriven.twitter.to.kafka.service.transformer;

import com.kosh.eventDriven.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

import java.util.Date;

@Component
public class TwitterStatusToAvroTransformer {

    public TwitterAvroModel getTwitterAvroModelFromStatus(Status status) {
        return TwitterAvroModel
                .newBuilder()
                .setId(status.getId())
                .setUserId(status.getId())
                .setText(status.getText())
                .setCreatedAt(new Date().getTime())
                .build();
    }
}
