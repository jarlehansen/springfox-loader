package com.github.springfox.loader;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ActiveProfilesCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String[] profiles = environment.getProperty("springfox.activeProfiles", String[].class);
        return profiles == null || profiles.length == 0 || environment.acceptsProfiles(profiles);
    }
}
