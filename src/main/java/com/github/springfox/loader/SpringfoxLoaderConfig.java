package com.github.springfox.loader;

import com.github.springfox.loader.plugins.LoaderOperationPlugin;
import com.github.springfox.loader.plugins.LoaderTagProvider;
import com.github.springfox.loader.valueproperties.ValuePropertiesController;
import com.github.springfox.loader.valueproperties.ValuePropertiesLocator;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.DefaultTagsProvider;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EnableConfigurationProperties
@Configuration
@ComponentScan(basePackageClasses = SpringfoxLoaderConfig.class)
public class SpringfoxLoaderConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware, EmbeddedValueResolverAware {

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
        Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage(springfoxLoader.getBasePackage())::apply;
        if (springfoxLoader.listValueProps()) {
            Predicate<RequestHandler> listPropertiesRequestHandler = RequestHandlerSelectors.basePackage(ValuePropertiesController.class.getPackage().getName())::apply;
            predicate = predicate.or(listPropertiesRequestHandler);
        }

        if (springfoxLoader.includeControllers().length > 0) {
            Class<?>[] controllers = springfoxLoader.includeControllers();
            for (Class<?> controller : controllers) {
                Predicate<RequestHandler> includeControllerRequestHandler = RequestHandlerSelectors.basePackage(controller.getPackage().getName())::apply;
                predicate = predicate.or(includeControllerRequestHandler);
            }
        }

        apiSelectorBuilder.apis(predicate::test);

        apiSelectorBuilder.paths(PathSelectors.any()).build().apiInfo(apiInfo()).pathMapping(springfoxLoader.getPath());
        return apiSelectorBuilder.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(springfoxLoader.getTitle(), springfoxLoader.getDescription(), springfoxLoader.getVersion(),
                springfoxLoader.getTermsOfServiceUrl(), springfoxLoader.getContact(), springfoxLoader.getLicense(), springfoxLoader.getLicenseUrl(), getVendorExtensions());
    }

    private List<VendorExtension> getVendorExtensions() {
        Extension[] extensions = springfoxLoader.extensions();
        if (extensions.length == 1 && StringUtils.isEmpty(extensions[0].name())) {
            return Collections.emptyList();
        }

        return Arrays.stream(extensions).map(extension -> {
            ExtensionProperty[] extensionProperties = extension.properties();
            List<StringVendorExtension> vendorExtensions = Arrays.stream(extensionProperties).map(property -> new StringVendorExtension(property.name(), property.value())).collect(Collectors.toList());

            ObjectVendorExtension vendorExtension = new ObjectVendorExtension(extension.name());
            for (StringVendorExtension stringVendorExtension : vendorExtensions) {
                vendorExtension.addProperty(stringVendorExtension);
            }
            return vendorExtension;
        }).collect(Collectors.toList());
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

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (!StringUtils.isEmpty(springfoxLoader.swaggerUiBasePath())) {
            registry.addRedirectViewController(resourcePath("/v2/api-docs"), "/v2/api-docs");
            registry.addRedirectViewController(resourcePath("/swagger-resources/configuration/ui"), "/swagger-resources/configuration/ui");
            registry.addRedirectViewController(resourcePath("/swagger-resources/configuration/security"), "/swagger-resources/configuration/security");
            registry.addRedirectViewController(resourcePath("/swagger-resources"), "/swagger-resources");
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!StringUtils.isEmpty(springfoxLoader.swaggerUiBasePath())) {
            registry.addResourceHandler(resourcePath("/swagger-ui.html**")).addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
            registry.addResourceHandler(resourcePath("/webjars/**")).addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    private String resourcePath(String path) {
        return springfoxLoader.swaggerUiBasePath() + path;
    }
}
