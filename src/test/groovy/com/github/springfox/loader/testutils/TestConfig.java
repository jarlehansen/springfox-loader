package com.github.springfox.loader.testutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Value("${test.property:test123}")
    private String property;

}
