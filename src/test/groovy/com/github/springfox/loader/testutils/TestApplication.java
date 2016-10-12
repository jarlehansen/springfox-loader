package com.github.springfox.loader.testutils;

import com.github.springfox.loader.EnableSpringfox;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSpringfox(activeProfiles = "test")
@SpringBootApplication
public class TestApplication {
}
