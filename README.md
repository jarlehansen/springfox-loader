# Springfox Loader

[![Build Status](https://travis-ci.org/jarlehansen/springfox-loader.svg?branch=master)](https://travis-ci.org/jarlehansen/springfox-loader)

Simplifies the initialization of [Springfox](http://springfox.io/).

## Installation

The required springfox dependencies are included when you add the _Springfox Loader_ dependency.

_build.gradle_
```groovy
compile('com.github.springfox.loader:springfox-loader:_version_')
```

_pom.xml_
```groovy
<dependency>
    <groupId>com.github.springfox.loader</groupId>
    <artifactId>springfox-loader</artifactId>
    <version>_version_</version>
</dependency>
```

## Usage

Add `@EnableSpringfox` to the class containing the Spring boot main method (`@SpringBootApplication`).
This will automatically create the springfox configuration for you.

__Configuration options:__
* path - The path
* title -
* description
* version
* termsOfServiceUrl
* contactName
* contactUrl
* contactEmail
* license
* licenseUrl

### Properties

It is also possible to configure the values using properties/yml files (typically application.properties/application.yml).
On startup it will try to lookup the same configuration options as described above with 'springfox.' as a prefix.
For example springfox.path.

If both the annotation values and properties values are defined,