package com.github.springfox.loader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "springfox")
public class SpringfoxLoaderProps {

    private String path = "";

    private String name;

    private String title = "";

    private String description = "";

    private String version = "";

    private String termsOfServiceUrl = "";

    private String contactName = "";

    private String contactUrl = "";

    private String contactEmail = "";

    private String license = "";

    private String licenseUrl = "";

    private String swaggerUiBasePath = "";

    public String getTitle() {
        return (name == null) ? title : name;
    }
}
