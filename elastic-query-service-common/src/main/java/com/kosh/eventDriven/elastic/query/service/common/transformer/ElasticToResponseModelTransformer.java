package com.kosh.eventDriven.elastic.query.service.common.transformer;

import com.kosh.eventDriven.elastic.model.index.impl.TwitterIndexModel;
import com.kosh.eventDriven.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticToResponseModelTransformer {

    public ElasticQueryServiceResponseModel getResponseModel(TwitterIndexModel twitterIndexModel) {
        return ElasticQueryServiceResponseModel
                .builder()
                .id(twitterIndexModel.getId())
                .userId(twitterIndexModel.getUserId())
                .text(twitterIndexModel.getText())
                .build();
    }

    public List<ElasticQueryServiceResponseModel> getResponseModels(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::getResponseModel).collect(Collectors.toList());
    }
}
