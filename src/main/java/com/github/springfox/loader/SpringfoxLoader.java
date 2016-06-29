package com.github.springfox.loader;

import io.swagger.annotations.License;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringValueResolver;
import springfox.documentation.service.Contact;

import java.util.Map;

class SpringfoxLoader {
    private SpringfoxLoaderProps loaderProps;
    private EnableSpringfox annotation;
    private StringValueResolver stringValueResolver;

    void setSpringfoxLoaderProps(SpringfoxLoaderProps loaderProps) {
        this.loaderProps = loaderProps;
    }

    void setApplicationContext(ApplicationContext applicationContext) {
        this.annotation = getAnnotation(applicationContext);
    }

    void setStringValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
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
        return val(annotation.value().title(), loaderProps.getTitle());
    }

    String getDescription() {
        return val(annotation.value().description(), loaderProps.getDescription());
    }

    String getVersion() {
        return val(annotation.value().version(), loaderProps.getVersion());
    }

    String getTermsOfServiceUrl() {
        return val(annotation.value().termsOfService(), loaderProps.getTermsOfServiceUrl());
    }

    Contact getContact() {
        io.swagger.annotations.Contact contact = annotation.value().contact();
        String name = val(contact.name(), loaderProps.getContactName());
        String url = val(contact.url(), loaderProps.getContactUrl());
        String email = val(contact.email(), loaderProps.getContactEmail());
        return new Contact(name, url, email);
    }

    String getLicense() {
        License license = annotation.value().license();
        return val(license.name(), loaderProps.getLicense());
    }

    String getLicenseUrl() {
        License license = annotation.value().license();
        return val(license.url(), loaderProps.getLicenseUrl());
    }

    String val(String annotation, String prop) {
        if (annotation.matches("\\$\\{(.+)\\}")) {
            annotation = stringValueResolver.resolveStringValue(annotation);
        }

        return ("".equals(prop)) ? annotation : prop;
    }

    boolean springEndpointsEnabled() {
        return annotation.springEndpointsEnabled();
    }

    boolean simplifiedTags() {
        return annotation.simplifiedDefaultTags();
    }

}
