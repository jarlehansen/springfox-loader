package com.github.springfox.loader.testutils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public TestDto getTestDto() {
        return new TestDto("key", "value");
    }
}
