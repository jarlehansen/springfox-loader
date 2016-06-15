package com.github.springfox.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;

@Configuration
public class SpringfoxLoaderConfiguration implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(SpringfoxLoaderConfiguration.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSpringfox.class);
        if (beans.size() == 1) {
            Object bean = beans.values().iterator().next();
            EnableSpringfox annotation = AnnotationUtils.findAnnotation(bean.getClass(), EnableSpringfox.class);
        } else if (beans.size() > 1) {
            logger.warn("Expected 1 @EnableSpringfox annotation, but found {}", beans.size());
        }
    }
}
