package com.github.springfox.loader.controller;

import com.github.springfox.loader.SpringfoxLoaderProps;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/springfox-loader")
@ConditionalOnProperty(value = SpringfoxLoaderProps.PROPS_SPRINGFOX_ENDPOINTS, havingValue = "true")
public class SpringfoxLoaderController {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers;

    public SpringfoxLoaderController() {
        headers = new HttpHeaders();
        headers.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, Lists.newArrayList("*"));
    }

    @GetMapping("/api-docs")
    public ResponseEntity getApiDocs(HttpServletRequest request) throws IOException {
        UriComponents uri = ServletUriComponentsBuilder.fromServletMapping(request).path("/v2/api-docs").build();
        ResponseEntity<String> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, new HttpEntity<>(null), String.class);
        return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }
}
