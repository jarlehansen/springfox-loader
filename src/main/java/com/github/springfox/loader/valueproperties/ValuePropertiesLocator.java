package com.github.springfox.loader.valueproperties;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ValuePropertiesLocator {
    private Set<ValueProperty> properties = Collections.emptySet();

    public ValuePropertiesLocator(String packageName) {
        populateProperties(packageName);
    }

    void populateProperties(String packageName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packageName).addScanners(new FieldAnnotationsScanner()));
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Value.class);
        properties = fields.stream().map(f -> new ValueProperty(f.getAnnotation(Value.class).value())).collect(Collectors.toSet());
    }

    public Set<ValueProperty> getProperties() {
        return properties;
    }
}
