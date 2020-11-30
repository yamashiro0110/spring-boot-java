package org.yamashiro0110.sample.web;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

public class HttpRequestTest {
    private Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Test
    void httpRequest() {
        List.of(
            "http://localhost:8080/status/200",
            "http://localhost:8080/status/400",
            "http://localhost:8080/status/401",
            "http://localhost:8080/status/403",
            "http://localhost:8080/status/404",
            "http://localhost:8080/status/500",
            "http://localhost:8080/status/502",
            "http://localhost:8080/status/503",
            "http://localhost:8080/status/302"
        ).forEach(this::httpRequest);
    }

    void httpRequest(final String url) {
        try {
            this.logger.info("start http-request {}", url);
            final RestTemplate restTemplate = new RestTemplateBuilder().build();
            final RequestEntity<?> requestEntity = RequestEntity.get(URI.create(url)).build();
            final ResponseEntity<?> responseEntity = restTemplate.exchange(requestEntity, String.class);
            this.logger.info("result http-request status:{} body:{}", responseEntity.getStatusCode(), responseEntity.getBody());
        } catch (final HttpClientErrorException e) {
            this.logger.error("http-request client error {} {}", url, e.getStatusCode(), e);
        } catch (final HttpServerErrorException e) {
            this.logger.error("http-request server error {} {}", url, e.getStatusCode(), e);
        }
    }

}
