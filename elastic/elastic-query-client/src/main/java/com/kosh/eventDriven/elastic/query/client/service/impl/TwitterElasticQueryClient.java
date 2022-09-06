package com.kosh.eventDriven.elastic.query.client.service.impl;


import com.kosh.eventDriven.config.ElasticConfigData;
import com.kosh.eventDriven.config.ElasticQueryConfigData;
import com.kosh.eventDriven.elastic.model.index.impl.TwitterIndexModel;
import com.kosh.eventDriven.elastic.query.client.exception.ElasticQueryClientException;
import com.kosh.eventDriven.elastic.query.client.service.ElasticQueryClient;
import com.kosh.eventDriven.elastic.query.client.util.ElasticQueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {


    private final ElasticConfigData elasticConfigData;

    private final ElasticQueryConfigData elasticQueryConfigData;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;

    public TwitterElasticQueryClient(ElasticConfigData configData,
                                     ElasticQueryConfigData queryConfigData,
                                     ElasticsearchOperations elasticOperations,
                                     ElasticQueryUtil<TwitterIndexModel> queryUtil) {
        this.elasticConfigData = configData;
        this.elasticQueryConfigData = queryConfigData;
        this.elasticsearchOperations = elasticOperations;
        this.elasticQueryUtil = queryUtil;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchResult = elasticsearchOperations.searchOne(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        if (searchResult == null) {
            log.error("No document found at elasticsearch with id {}", id);
            throw new ElasticQueryClientException("No document found at elasticsearch with id " + id);
        }
        log.info("Document with id {} retrieved successfully", searchResult.getId());
        return searchResult.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(), text);
        return search(query, "{} of documents with text {} retrieved successfully", text);
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryForAll();
        return search(query, "{} number of documents retrieved successfully");
    }

    private List<TwitterIndexModel> search(Query query, String logMessage, Object... logParams) {
        SearchHits<TwitterIndexModel> searchResult = elasticsearchOperations.search(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        log.info(logMessage, searchResult.getTotalHits(), logParams);
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}