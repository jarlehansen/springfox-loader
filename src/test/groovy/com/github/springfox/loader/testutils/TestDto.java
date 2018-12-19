package com.github.springfox.loader.testutils;

public class TestDto {
    private String key;
    private String value;

    public TestDto() {
    }

    public TestDto(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
