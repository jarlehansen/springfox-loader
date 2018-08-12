package com.github.springfox.loader.testutils;

import com.github.springfox.loader.EnableSpringfox;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSpringfox(
        value = @Info(title = "test", version = "1.0.0",
                extensions = @Extension(name = "x-test",
                        properties = @ExtensionProperty(name = "test-key", value = "test-value")
                )
        ),
        swaggerUiBasePath = "/docs", includeControllers = TestController.class)
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
