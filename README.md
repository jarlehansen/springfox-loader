# Springfox Loader

[![Build Status](https://travis-ci.org/jarlehansen/springfox-loader.svg?branch=master)](https://travis-ci.org/jarlehansen/springfox-loader)

Simplifies the initialization of [Springfox](http://springfox.io/).

## Installation

_build.gradle_
```groovy
compile('com.github.springfox.loader:springfox-loader:version...')
```

_pom.xml_
```groovy
<dependency>
    <groupId>com.github.springfox.loader</groupId>
    <artifactId>springfox-loader</artifactId>
    <version> ... </version>
</dependency>
```

## Usage

Add `@EnableSpringfox` to the class containing the Spring boot main method (`@SpringBootApplication`).
This will automatically create the springfox configuration for you.

__Configuration options:__
* path
* title
* description
* version
* termsOfServiceUrl
* contactName
* contactUrl
* contactEmail
* license
* licenseUrl