package com.github.springfox.loader.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.ControllerNamingUtils;
import springfox.documentation.spring.web.readers.operation.DefaultTagsProvider;


public class LoaderTagProvider extends DefaultTagsProvider {
    private final boolean conventionMode;

    public LoaderTagProvider(boolean conventionMode) {
        this.conventionMode = conventionMode;
    }

    @Override
    public ImmutableSet<String> tags(OperationContext context) {
        if (conventionMode) {
            String controllerName = ControllerNamingUtils.controllerNameAsGroup(context.getHandlerMethod());
            controllerName = controllerName.replace("-controller", "");
            return ImmutableSet.copyOf(Sets.newHashSet(new String[]{controllerName}));
        } else {
            return super.tags(context);
        }
    }
}
