package com.kosh.eventDriven.elastic.index.client.service;

import com.kosh.eventDriven.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
