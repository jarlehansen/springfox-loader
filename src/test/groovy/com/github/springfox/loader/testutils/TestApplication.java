package com.github.springfox.loader.testutils;

import com.github.springfox.loader.EnableSpringfox;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSpringfox(swaggerUiBasePath = "/docs", includeControllers = TestController.class)
@SpringBootApplication
public class TestApplication {
}
