# Springfox Loader

[![Build Status](https://travis-ci.org/jarlehansen/springfox-loader.svg?branch=master)](https://travis-ci.org/jarlehansen/springfox-loader)

Simplifies the initialization of [Springfox](http://springfox.io/).
It does not offer all the flexibility and configuration options available in Springfox, but is meant as a simple way to
get swagger up and running without the need for a lot of configuration.

## Installation

The required springfox dependencies are included when you add the _Springfox Loader_ dependency.

_build.gradle_
```groovy
compile('com.github.springfox.loader:springfox-loader:[version]')
```

_pom.xml_
```groovy
<dependency>
    <groupId>com.github.springfox.loader</groupId>
    <artifactId>springfox-loader</artifactId>
    <version>[version]</version>
</dependency>
```

## Usage

Add `@EnableSpringfox` to the class containing the Spring boot main method (`@SpringBootApplication`).
This will automatically create the springfox configuration for you.

__Configuration options:__
* path - Servlet path mapping
* @Info
 * title
 * description
 * version
 * termsOfServiceUrl
* @Contact
 * name
 * url
 * email
* @License
 * name
 * url

### Properties

It is also possible to configure the values using properties/yml files (typically application.properties/application.yml).
On startup it will try to lookup the same configuration options as described above with 'springfox.' as a prefix.
For example springfox.path.

If both the annotation values and properties values are defined the values from the properties/yml-file is used.

__Property keys__
* springfox.path
* spring.application.name _or_ springfox.title
* springfox.description
* springfox.version
* springfox.terms-of-service-url
* springfox.contact.name
* springfox.contact.url
* springfox.contact.email
* springfox.license.name
* springfox.license.url