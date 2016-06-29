package com.github.springfox.loader.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.ControllerNamingUtils;
import springfox.documentation.spring.web.readers.operation.DefaultTagsProvider;


public class LoaderTagProvider extends DefaultTagsProvider {
    private final boolean simplifiedTags;

    public LoaderTagProvider(boolean simplifiedTags) {
        this.simplifiedTags = simplifiedTags;
    }

    @Override
    public ImmutableSet<String> tags(OperationContext context) {
        if (simplifiedTags) {
            String controllerName = ControllerNamingUtils.controllerNameAsGroup(context.getHandlerMethod());
            controllerName = controllerName.replace("-controller", "");
            return ImmutableSet.copyOf(Sets.newHashSet(new String[]{controllerName}));
        } else {
            return super.tags(context);
        }
    }
}
