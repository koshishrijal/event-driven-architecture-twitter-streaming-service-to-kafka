package com.kosh.eventDriven.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "elastic-config")
public class ElasticConfigData {
    private String indexName;
    private String connectionUrl;
    private Integer connectTimeoutMs;
    private Integer socketTimeoutMs;
}
