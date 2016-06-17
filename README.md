# Springfox Loader

[![Build Status](https://travis-ci.org/jarlehansen/springfox-loader.svg?branch=master)](https://travis-ci.org/jarlehansen/springfox-loader)

Simplifies the initialization of [Springfox](http://springfox.io/).
It does not offer all the flexibility and configuration options available in Springfox, but is created to be a simple way to
get spring + swagger up and running without the need for a lot of configuration.

__Features:__
* Annotation-based configuration
* Support for standard Spring application-configuration in properties/yml files
* Support for using Spring placeholder values `${...}`  in the configuration
* Easy to extend by using the standard Springfox classes


## Installation

The required springfox dependencies are included when you add the _Springfox Loader_ dependency.

The jar-file is hosted in [JCenter](https://bintray.com/jarlehansen/maven/springfox-loader/)

### Gradle

_build.gradle_
```groovy
compile('com.github.springfox.loader:springfox-loader:0.0.5')
```

### Maven

_pom.xml_
```xml
<dependency>
    <groupId>com.github.springfox.loader</groupId>
    <artifactId>springfox-loader</artifactId>
    <version>0.0.5</version>
</dependency>
```

## Usage

Add `@EnableSpringfox` to the class containing the Spring boot main method (`@SpringBootApplication`).
This will automatically create the springfox configuration for you.

The required values in the `@EnableSpringfox` is title and version (using `@Info`).

It is also possible to add Spring placeholders (with the `${...}` syntax) as values in the annotation.
This can be useful if you want to add values that are defined in for example properties/yml files.
In the example below the value `${version}` can be added in for example the application.properties-file

 __Examples__
 ```java
@EnableSpringfox(
        @Info(title = "title", version = "${version}")
)
 ```

```java
@EnableSpringfox(
    springEndpointsEnabled = false,
    value = @Info(
         title = "",
         version = "",
         description = "",
         termsOfService = "",
         contact = @Contact(name = "", url = "", email = ""),
         license = @License(name = "", url = ""))
 )
```

The _springEndpointsEnabled_ will display/hide the standard Spring endpoints, such as the endpoints added by [Spring actuator](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html).

### Properties

It is also possible to configure the values using properties/yml files (typically _application.properties_ / _application.yml_).
On startup it will try to lookup the same configuration options as described above with 'springfox.' as a prefix.
For example springfox.path.

If both the annotation values and properties values are defined the values from the properties/yml-file is used.

__Application properties__
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

### Swagger UI

The swagger-ui dependency is already included by Springfox Loader.
After enabling Springfox Loader you can access the webpage: http://localhost:8080/swagger-ui.html

A list of the swagger resources are available here: http://localhost:8080/swagger-resources

### Custom options
If there are options that are available in Springfox, but not the Springfox-loader it is possible to add it manually.
You can simply autowire the [Docket-object](http://springfox.github.io/springfox/javadoc/current/springfox/documentation/spring/web/plugins/Docket.html) and can alter the setup as needed.

```java
@Autowired
private Docket docket;

@PostConstruct
public void init() {
    docket.apiInfo(new ApiInfo("My new title", "", "1.0.0.", "", new Contact("", "", ""), "", ""));
}
```

### References
* [Springfox Reference Documentation](http://springfox.github.io/springfox/docs/current/)
* [Swagger Core Annotations](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)