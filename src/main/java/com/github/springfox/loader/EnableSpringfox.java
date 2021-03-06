package com.github.springfox.loader;

import io.swagger.annotations.Info;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringfoxLoaderConfig.class, SpringfoxLoaderProps.class, Swagger2DocumentationConfiguration.class})
public @interface EnableSpringfox {
    Info value() default @Info(title = "", version = "");

    String path() default "";

    String swaggerUiBasePath() default "";

    boolean convention() default true;

    Class<?>[] includeControllers() default {};

}
