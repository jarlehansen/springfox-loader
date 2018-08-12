package com.github.springfox.loader;

import io.swagger.annotations.Extension;
import io.swagger.annotations.License;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringValueResolver;
import springfox.documentation.service.Contact;

import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

class SpringfoxLoader {
    private SpringfoxLoaderProps loaderProps;
    private String packageName;
    private EnableSpringfox annotation;
    private StringValueResolver stringValueResolver;

    void setSpringfoxLoaderProps(SpringfoxLoaderProps loaderProps) {
        this.loaderProps = loaderProps;
    }

    void setApplicationContext(ApplicationContext applicationContext) {
        this.annotation = getAnnotation(applicationContext);
        this.packageName = getPackageName(applicationContext);
    }

    void setStringValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }

    private Object getBeanWithAnnotation(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSpringfox.class);
        if (beans.size() == 1) {
            return beans.values().iterator().next();
        } else {
            throw new IllegalStateException("Expected to find 1 @EnableSpringfox annotation, but found " + beans.size());
        }
    }

    String getPackageName(ApplicationContext applicationContext) {
        Object bean = getBeanWithAnnotation(applicationContext);
        return bean.getClass().getPackage().getName();
    }

    EnableSpringfox getAnnotation(ApplicationContext applicationContext) {
        Object bean = getBeanWithAnnotation(applicationContext);
        return AnnotationUtils.findAnnotation(bean.getClass(), EnableSpringfox.class);
    }

    String getPath() {
        return val(annotation.path(), loaderProps.getPath());
    }

    String getTitle() {
        String annotationTitle = annotation.value().title();
        String loaderTitle = loaderProps.getTitle();
        if (isEmpty(annotationTitle) && isEmpty(loaderTitle)) {
            throw new IllegalArgumentException("Missing property springfox.title (or spring.application.name)");
        }

        return val(annotation.value().title(), loaderProps.getTitle());
    }

    String getVersion() {
        String annotationVersion = annotation.value().version();
        String loaderVersion = loaderProps.getVersion();
        if (isEmpty(annotationVersion) && isEmpty(loaderVersion)) {
            throw new IllegalArgumentException("Missing property springfox.version");
        }

        return val(annotation.value().version(), loaderProps.getVersion());
    }

    String getDescription() {
        return val(annotation.value().description(), loaderProps.getDescription());
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

    boolean convention() {
        return annotation.convention();
    }

    String swaggerUiBasePath() {
        return val(annotation.swaggerUiBasePath(), loaderProps.getSwaggerUiBasePath());
    }

    String getBasePackage() {
        return packageName;
    }

    Class<?>[] includeControllers() {
        return annotation.includeControllers();
    }

    Extension[] extensions() {
        return annotation.value().extensions();
    }

    String val(String annotation, String prop) {
        String annotationValue = annotation;
        if (annotation.matches("\\$\\{(.+)}")) {
            annotationValue = stringValueResolver.resolveStringValue(annotation);
        }

        return ("".equals(prop)) ? annotationValue : prop;
    }
}
