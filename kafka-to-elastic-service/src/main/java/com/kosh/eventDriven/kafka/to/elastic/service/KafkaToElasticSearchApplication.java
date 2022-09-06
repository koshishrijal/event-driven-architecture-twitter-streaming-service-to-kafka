package com.kosh.eventDriven.kafka.to.elastic.service;

import com.kosh.eventDriven.config.KafkaConfigData;
import com.kosh.eventDriven.config.RetryConfigData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.kosh.*"})
public class KafkaToElasticSearchApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticSearchApplication.class);
    }

    @Autowired
    private  KafkaConfigData kafkaConfigData;


    @Autowired
    private RetryConfigData retryConfigData;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("retry data"+retryConfigData.toString());
        System.out.println("kakfa config data"+kafkaConfigData.toString());
    }
}
