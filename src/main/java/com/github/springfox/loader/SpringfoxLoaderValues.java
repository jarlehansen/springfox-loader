package com.github.springfox.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringfoxLoaderValues {

    @Value("${springfox.path")
    private String path;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${springfox.name}")
    private String springfoxName;

    @Value("${springfox.description}")
    private String description;

    @Value("${springfox.version}")
    private String version;

    @Value("${springfox.terms-of-service-url")
    private String termsOfServiceUrl;

    @Value("${springfox.contact-name}")
    private String contactName;

    @Value("${springfox.contact-url}")
    private String contactUrl;

    @Value("${springfox.license}")
    private String license;

    @Value("${springfox.license-url}")
    private String licenseUrl;

    public String getPath() {
        return path;
    }

    public String getName() {
        if(springfoxName == null) {
            return appName;
        } else {
            return springfoxName;
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

    public String getLicense() {
        return license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }
}
