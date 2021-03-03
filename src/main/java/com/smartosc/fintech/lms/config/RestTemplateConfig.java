package com.smartosc.fintech.lms.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.connection.timeout:3000}")
    private int restTemplateConnTimeout;
    @Value("${resttemplate.reading.timeout:3000}")
    private int restTemplateReadTimeout;

    @Bean
    @Qualifier("RT")
    public RestTemplate newrestTemplate() {
        SimpleClientHttpRequestFactory factoryOrigin = new SimpleClientHttpRequestFactory();
        factoryOrigin.setConnectTimeout(restTemplateConnTimeout);
        factoryOrigin.setReadTimeout(restTemplateReadTimeout);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(factoryOrigin);

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(new InOutLogInterceptorConfig()));
        return restTemplate;
    }

    @Bean
    @Qualifier("internalRT")
    public RestTemplate getTemplate() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpComponentsClientHttpRequestFactory factoryOrigin = new HttpComponentsClientHttpRequestFactory(httpClient);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(factoryOrigin);

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(
                new InOutLogInterceptorConfig()));
        return restTemplate;
    }

    @Bean
    @Qualifier("LOS_API_RT")
    public RestTemplate buildTemplateDmp() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpComponentsClientHttpRequestFactory factoryOrigin = new HttpComponentsClientHttpRequestFactory(httpClient);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(factoryOrigin);

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(
                new InOutLogInterceptorConfig()));

        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);

        return restTemplate;
    }

}
