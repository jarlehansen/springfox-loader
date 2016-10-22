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

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

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
