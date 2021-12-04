package com.altimetrik.altimetrics.configuration;

import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@org.springframework.context.annotation.Configuration
public class Configuration {



    @Bean
    public RallyRestApi getRallyRestApi() throws URISyntaxException {
        RallyRestApi restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"), "_qWXWE5zFSnaScdgrPS101sLL244hokaAm37YNDiRtq8");
        restApi.setWsapiVersion("v2.0");
        restApi.setApplicationName("Alti Metrics");
        return restApi;
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return  restTemplate;
    };



}
