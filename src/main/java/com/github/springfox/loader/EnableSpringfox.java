package com.github.springfox.loader;

import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringfoxLoaderConfiguration.class, SpringfoxLoaderValues.class, Swagger2DocumentationConfiguration.class})
public @interface EnableSpringfox {
    String path() default "/";

    String title() default "";

    String description() default "";

    String version() default "";

    String termsOfServiceUrl() default "";

    String contactName() default "";

    String contactUrl() default "";

    String license() default "";

    String licenseUrl() default "";
}
