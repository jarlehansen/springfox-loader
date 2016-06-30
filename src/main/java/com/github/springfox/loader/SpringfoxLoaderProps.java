package com.github.springfox.loader;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

    public String getPath() {
        return path;
    }

    public String getTitle() {
        if (name == null) {
            return title;
        } else {
            return name;
        }
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getLicense() {
        return license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }
}
