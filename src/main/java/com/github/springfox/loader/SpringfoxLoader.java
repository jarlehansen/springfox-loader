package com.github.springfox.loader;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;

class SpringfoxLoader {
    private final SpringfoxLoaderValues loaderValues;
    private final EnableSpringfox annotation;

    SpringfoxLoader(SpringfoxLoaderValues loaderValues, ApplicationContext applicationContext) {
        this.loaderValues = loaderValues;
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
}
