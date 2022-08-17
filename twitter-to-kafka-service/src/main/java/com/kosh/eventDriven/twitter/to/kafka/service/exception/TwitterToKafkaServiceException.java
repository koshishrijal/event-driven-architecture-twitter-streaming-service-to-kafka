package com.kosh.eventDriven.twitter.to.kafka.service.exception;

public class TwitterToKafkaServiceException extends RuntimeException {
    public TwitterToKafkaServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwitterToKafkaServiceException() {
        super();
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }


}
