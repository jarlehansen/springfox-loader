package com.github.springfox.loader.valueproperties;

public class ValueProperty {
    private String key;
    private String defaultValue;

    public ValueProperty(String annotationValue) {
        populateProperties(annotationValue);
    }

    private void populateProperties(String annotationValue) {
        String annotation = annotationValue.replace("${", "").replace("}", "");
        if (annotation.contains(":")) {
            int index = annotation.indexOf(":");
            key = annotation.substring(0, index);
            defaultValue = annotation.substring(index + 1);
        } else {
            key = annotation;
        }
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
