package com.kosh.eventDriven.elastic.query.web.client.api;

import com.kosh.eventDriven.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.kosh.eventDriven.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;
import com.kosh.eventDriven.elastic.query.web.client.service.ElasticQueryWebClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class QueryController {


    private final ElasticQueryWebClient elasticQueryWebClient;

    public QueryController(ElasticQueryWebClient webClient) {
        this.elasticQueryWebClient = webClient;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("elasticQueryWebClientRequestModel",
                ElasticQueryWebClientRequestModel.builder().build());
        return "home";
    }

    @PostMapping("/query-by-text")
    public String queryByText(@Valid ElasticQueryWebClientRequestModel requestModel,
                              Model model) {
        log.info("Querying with text {}", requestModel.getText());
        List<ElasticQueryWebClientResponseModel> responseModels = elasticQueryWebClient.getDataByText(requestModel);
      //  List<ElasticQueryWebClientResponseModel> responseModels = new ArrayList<>();
        responseModels.add(ElasticQueryWebClientResponseModel.builder().id("1").text(requestModel.getText()).build());
        model.addAttribute("elasticQueryWebClientResponseModels", responseModels);
        model.addAttribute("searchText", requestModel.getText());
        model.addAttribute("elasticQueryWebClientRequestModel",
                ElasticQueryWebClientRequestModel.builder().build());
        return "home";
    }

}