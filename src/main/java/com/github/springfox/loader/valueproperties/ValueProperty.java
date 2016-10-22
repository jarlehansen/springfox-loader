package com.github.springfox.loader.valueproperties;

public class ValueProperty {
    private String key;
    private String defaultValue;

    public ValueProperty(String annotationValue) {
        populateProperties(annotationValue);
    }

    private void populateProperties(String annotationValue) {
        annotationValue = annotationValue.replace("${", "");
        annotationValue = annotationValue.replace("}", "");
        if (annotationValue.contains(":")) {
            int index = annotationValue.indexOf(":");
            key = annotationValue.substring(0, index);
            defaultValue = annotationValue.substring(index + 1);
        } else {
            key = annotationValue;
        }
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
