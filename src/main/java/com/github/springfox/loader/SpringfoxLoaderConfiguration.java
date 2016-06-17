package com.github.springfox.loader;

import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringValueResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringfoxLoaderConfiguration implements ApplicationContextAware, EmbeddedValueResolverAware {

    private final SpringfoxLoader springfoxLoader = new SpringfoxLoader();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springfoxLoader.setApplicationContext(applicationContext);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        springfoxLoader.setStringValueResolver(stringValueResolver);
    }

    @Autowired
    public void setSpringfoxLoaderProps(SpringfoxLoaderProps loaderProps) {
        springfoxLoader.setSpringfoxLoaderProps(loaderProps);
    }

    @Bean
    public Docket api() {
        ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.SWAGGER_2).select();
        if (springfoxLoader.springEndpointsEnabled()) {
            apiSelectorBuilder.apis(RequestHandlerSelectors.any());
        } else {
            apiSelectorBuilder.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework")));
        }

        apiSelectorBuilder.paths(PathSelectors.any()).build().apiInfo(apiInfo()).pathMapping(springfoxLoader.getPath());
        return apiSelectorBuilder.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(springfoxLoader.getTitle(), springfoxLoader.getDescription(), springfoxLoader.getVersion(),
                springfoxLoader.getTermsOfServiceUrl(), springfoxLoader.getContact(), springfoxLoader.getLicense(), springfoxLoader.getLicenseUrl());
    }
}
