# Springfox Loader

[![Build Status](https://travis-ci.org/jarlehansen/springfox-loader.svg?branch=master)](https://travis-ci.org/jarlehansen/springfox-loader)
[![Coverage Status](https://coveralls.io/repos/github/jarlehansen/springfox-loader/badge.svg?branch=master)](https://coveralls.io/github/jarlehansen/springfox-loader?branch=master)

Simplifies the initialization of [Springfox](http://springfox.io/).
It does not offer all the flexibility and configuration options available in Springfox, but is created to be a simple way to
get spring + swagger up and running without the need for a lot of configuration.

__Features:__
* Annotation-based configuration
* Support for standard Spring application-configuration in properties/yml files
* Support for using Spring placeholder values `${...}`  in the configuration
* Easy to extend by using the standard Springfox classes
* Convention based naming of values displayed in swagger-ui, minimizing the need for manual configuration

---

* [Installation](#installation)
 * [Gradle](#gradle)
 * [Maven](#maven)
* [Usage](#usage)
 * [Properties](#properties)
 * [Swagger UI](#swagger-ui)
 * [Custom options](#custom-options)
 * [References](#references)

## Installation

The required springfox dependencies are included when you add the _Springfox Loader_ dependency.

The jar-file available in [JCenter](https://bintray.com/jarlehansen/maven/springfox-loader/).

### Gradle

_build.gradle_
```groovy
compile('com.github.springfox.loader:springfox-loader:1.2.0')
```

### Maven

_pom.xml_
```xml
<dependency>
    <groupId>com.github.springfox.loader</groupId>
    <artifactId>springfox-loader</artifactId>
    <version>1.1.2</version>
</dependency>
```

## Usage

Add `@EnableSpringfox` to the class containing the Spring boot main method (`@SpringBootApplication`).
This will automatically create the springfox configuration for you.

The required values in the `@EnableSpringfox` is title and version. You can set these values either by using `@Info` or
with the properties _springfox.title_ and _springfox.version_.

It is also possible to add Spring placeholders (with the `${...}` syntax) as values in the annotation.
This can be useful if you want to add values that are defined in for example properties/yml files.
In the example below the value `${version}` can be added in for example the application.properties-file

 __Minimal examples__
 ```java
@EnableSpringfox(
        @Info(title = "title", version = "${version}")
)
 ```

_or add configuration properties_
```
springfox.title=my-app
springfox.version=1.0.0
```

```java
@EnableSpringfox
```


__Full example__
```java
@EnableSpringfox(
    conventionMode = false,
    swaggerUiBasePath = "",
    includeControllers = MyController.class,    
    value = @Info(
         title = "",
         version = "",
         description = "",
         termsOfService = "",
         contact = @Contact(name = "", url = "", email = ""),
         license = @License(name = "", url = ""),
         extensions = @Extension(name = "x-test",
            properties = @ExtensionProperty(name = "test-key", value = "test-value")
         ))
)
```

* Use __conventionMode__ to print better names on the swagger-ui page. It will alter the tags (the name of the groups).
It will remove  _Controller_ at the end of the text if it is present. Additionally, it will split the operation name by
replacing camelcase with space and uppercasing the word (for example the method `getCustomer()` will be displayed as `Get customer`).
If the `@ApiOperation` annotation is present, these values will be used.
* __swaggerUiBasePath__ customize the base path to swagger-ui. If the value is for example '/documentation', the path to swagger-ui will be '/documentation/swagger-ui.html'.
* __includeControllers__ add controllers to the swagger configuration that is not registered in the default base package (which is based on the Application class).

### Properties

It is also possible to configure the values using properties/yml files (typically _application.properties_ / _application.yml_).
On startup it will try to lookup the same configuration options as described above with 'springfox.' as a prefix.
For example springfox.path.

If both the annotation values and properties values are defined the values from the properties/yml-file is used.

__Application properties__
* springfox.path
* springfox.title _or_ spring.application.name
* springfox.description
* springfox.version
* springfox.terms-of-service-url
* springfox.contact.name
* springfox.contact.url
* springfox.contact.email
* springfox.license.name
* springfox.license.url
* springfox.activeProfiles - _Enable springfox for the configured profiles. If not set, all profiles loads springfox. Default is all profiles._
* springfox.swagger-ui-base-path

### Swagger UI

The swagger-ui dependency is already included by Springfox Loader.
After enabling Springfox Loader you can access the webpage: `http://localhost:8080/swagger-ui.html`  
The base path to swagger-ui can be customized with the `springfox.swagger-ui-base-path`.

A list of the swagger resources are available here: http://localhost:8080/swagger-resources

### Custom options
If there are options that are available in Springfox, but not the Springfox-loader it is possible to add it manually.
You can simply autowire the [Docket-object](http://springfox.github.io/springfox/javadoc/current/springfox/documentation/spring/web/plugins/Docket.html) and can alter the setup as needed.

```java
@Autowired
private Docket docket;

@PostConstruct
public void init() {
    docket.apiInfo(new ApiInfo("My new title", "", "1.0.0", "", new Contact("", "", ""), "", ""));
}
```

### References
* [Springfox Reference Documentation](http://springfox.github.io/springfox/docs/current/)
* [Swagger Core Annotations](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)