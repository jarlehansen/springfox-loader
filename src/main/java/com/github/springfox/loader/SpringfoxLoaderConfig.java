package com.github.springfox.loader;

import com.github.springfox.loader.plugins.LoaderOperationPlugin;
import com.github.springfox.loader.plugins.LoaderTagProvider;
import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.StringValueResolver;
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
    private Environment environment;

    private SpringfoxLoader springfoxLoader = new SpringfoxLoader();


    @Value("${springfox.activeProfile:true}")
    private boolean activeProfile;

    @PostConstruct
    public void init() {
        String activeProfiles = springfoxLoader.getActiveProfiles();
        if (activeProfiles.length() > 0) {
            activeProfile = environment.acceptsProfiles(activeProfiles.split(","));
        }
    }

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
    @ConditionalOnExpression("'${springfox.activeProfile}'=='true'")
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
    @ConditionalOnExpression("'${springfox.activeProfile}'=='true'")
    public DefaultTagsProvider loaderDefaultTagsProvider() {
        return new LoaderTagProvider(springfoxLoader.conventionMode());
    }

    @Bean
    @ConditionalOnExpression("'${springfox.activeProfile}'=='true'")
    public LoaderOperationPlugin loaderOperationPlugin() {
        return new LoaderOperationPlugin(springfoxLoader.conventionMode());
    }
}
