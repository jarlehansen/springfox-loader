package com.github.springfox.loader.plugins;

import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.swagger.common.SwaggerPluginSupport;


@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class LoaderOperationPlugin implements OperationBuilderPlugin {
    private final boolean conventionMode;

    public LoaderOperationPlugin(boolean conventionMode) {
        this.conventionMode = conventionMode;
    }

    @Override
    public void apply(OperationContext operationContext) {
        if (conventionMode) {
            String summary = operationContext.operationBuilder().build().getSummary();
            String newSummary = Paths.splitCamelCase(summary, " ").toLowerCase();
            operationContext.operationBuilder().summary(StringUtils.capitalize(newSummary));
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }
}
