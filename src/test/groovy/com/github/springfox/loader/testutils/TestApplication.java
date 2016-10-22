package com.github.springfox.loader.testutils;

import com.github.springfox.loader.EnableSpringfox;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSpringfox(listValueProps = true)
@SpringBootApplication
public class TestApplication {
}
