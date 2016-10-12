package com.github.springfox.loader;

import com.github.springfox.loader.plugins.LoaderOperationPlugin;
import com.github.springfox.loader.plugins.LoaderTagProvider;
import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.*;
import org.springframework.util.StringValueResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.DefaultTagsProvider;

@EnableConfigurationProperties
@Configuration
@ComponentScan(basePackageClasses = SpringfoxLoaderConfig.class)
public class SpringfoxLoaderConfig implements ApplicationContextAware, EmbeddedValueResolverAware {

    private SpringfoxLoader springfoxLoader = new SpringfoxLoader();

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
    @Conditional(ActiveProfilesCondition.class)
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

    @Bean
    @Primary
    @Conditional(ActiveProfilesCondition.class)
    public DefaultTagsProvider loaderDefaultTagsProvider() {
        return new LoaderTagProvider(springfoxLoader.conventionMode());
    }

    @Bean
    @Conditional(ActiveProfilesCondition.class)
    public LoaderOperationPlugin loaderOperationPlugin() {
        return new LoaderOperationPlugin(springfoxLoader.conventionMode());
    }
}
