package com.github.springfox.loader;

import com.github.springfox.loader.plugins.LoaderOperationPlugin;
import com.github.springfox.loader.plugins.LoaderTagProvider;
import com.github.springfox.loader.valueproperties.ValuePropertiesController;
import com.github.springfox.loader.valueproperties.ValuePropertiesLocator;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.*;
import org.springframework.util.StringValueResolver;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.DefaultTagsProvider;

import javax.annotation.PostConstruct;

@EnableConfigurationProperties
@Configuration
@ComponentScan(basePackageClasses = SpringfoxLoaderConfig.class)
public class SpringfoxLoaderConfig implements ApplicationContextAware, EmbeddedValueResolverAware {

    @Autowired
    private ValuePropertiesController valuePropertiesController;

    private SpringfoxLoader springfoxLoader = new SpringfoxLoader();
    private ValuePropertiesLocator valuePropertiesLocator;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springfoxLoader.setApplicationContext(applicationContext);
        if (springfoxLoader.listValueProps()) {
            valuePropertiesLocator = new ValuePropertiesLocator(springfoxLoader.getBasePackage());
        }
    }

    @PostConstruct
    public void init() {
        valuePropertiesController.setValuePropertiesLocator(valuePropertiesLocator);
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

        Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage(springfoxLoader.getBasePackage());
        Predicate<RequestHandler> listPropertiesRequestHandler = RequestHandlerSelectors.basePackage(ValuePropertiesController.class.getPackage().getName());
        if (springfoxLoader.listValueProps()) {
            predicate = Predicates.or(predicate, listPropertiesRequestHandler);
        }
        apiSelectorBuilder.apis(predicate);

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

    ValuePropertiesLocator valuePropertiesLocator() {
        return valuePropertiesLocator;
    }
}
