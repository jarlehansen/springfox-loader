package com.github.springfox.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringfoxLoaderProps {

    @Value("${springfox.path:")
    private String path;


    @Value("${spring.application.name}")
    private String name;
    @Value("${springfox.title:}")
    private String title;

    @Value("${springfox.description:}")
    private String description;

    @Value("${springfox.version:}")
    private String version;

    @Value("${springfox.terms-of-service-url:}")
    private String termsOfServiceUrl;

    @Value("${springfox.contact-name:}")
    private String contactName;

    @Value("${springfox.contact-url:}")
    private String contactUrl;

    @Value("${springfox.contact-email:}")
    private String contactEmail;

    @Value("${springfox.license:}")
    private String license;

    @Value("${springfox.license-url:}")
    private String licenseUrl;

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
