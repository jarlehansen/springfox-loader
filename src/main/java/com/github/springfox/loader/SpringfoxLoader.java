package com.github.springfox.loader;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import springfox.documentation.service.Contact;

import java.util.Map;

class SpringfoxLoader {
    private SpringfoxLoaderProps loaderProps;
    private EnableSpringfox annotation;

    SpringfoxLoader() {
    }

    SpringfoxLoader(SpringfoxLoaderProps loaderProps, ApplicationContext applicationContext) {
        this.loaderProps = loaderProps;
        this.annotation = getAnnotation(applicationContext);
    }

    EnableSpringfox getAnnotation(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSpringfox.class);
        if (beans.size() == 1) {
            Object bean = beans.values().iterator().next();
            return AnnotationUtils.findAnnotation(bean.getClass(), EnableSpringfox.class);
        } else {
            throw new IllegalStateException("Expected to find 1 @EnableSpringfox annotation, but found " + beans.size());
        }
    }

    String getPath() {
        return val(annotation.path(), loaderProps.getPath());
    }

    String getTitle() {
        return val(annotation.title(), loaderProps.getTitle());
    }

    String getDescription() {
        return val(annotation.description(), loaderProps.getDescription());
    }

    String getVersion() {
        return val(annotation.version(), loaderProps.getVersion());
    }

    String getTermsOfServiceUrl() {
        return val(annotation.termsOfServiceUrl(), loaderProps.getTermsOfServiceUrl());
    }

    Contact getContact() {
        String name = val(annotation.contactName(), loaderProps.getContactName());
        String url = val(annotation.contactUrl(), loaderProps.getContactUrl());
        String email = val(annotation.contactEmail(), loaderProps.getContactEmail());
        return new Contact(name, url, email);
    }

    String getLicense() {
        return val(annotation.license(), loaderProps.getLicense());
    }

    String getLicenseUrl() {
        return val(annotation.licenseUrl(), loaderProps.getLicenseUrl());
    }

    private String val(String annotation, String prop) {
        return ("".equals(annotation)) ? prop : annotation;
    }

}