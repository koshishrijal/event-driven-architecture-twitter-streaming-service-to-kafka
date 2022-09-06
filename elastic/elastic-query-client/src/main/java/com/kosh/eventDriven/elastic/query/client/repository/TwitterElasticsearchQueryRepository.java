package com.kosh.eventDriven.elastic.query.client.repository;

import com.kosh.eventDriven.elastic.model.index.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwitterElasticsearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel, String> {
    List<TwitterIndexModel> findByText(String text);
}
