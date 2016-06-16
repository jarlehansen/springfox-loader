# Springfox Loader

[![Build Status](https://travis-ci.org/jarlehansen/springfox-loader.svg?branch=master)](https://travis-ci.org/jarlehansen/springfox-loader)

Simplifies the initialization of [Springfox](http://springfox.io/).
It does not offer all the flexibility and configuration options available in Springfox, but is meant as a simple way to
get swagger up and running without the need for a lot of configuration.

__Features__
* Annotation-based configuration
* Support for standard Spring application-configuration in properties/yml files
* Support for using Spring placeholder values (_${...}_) in the configuration
* Easy to extend by using the Springfox classes

## Installation

The required springfox dependencies are included when you add the _Springfox Loader_ dependency.

### Gradle

_build.gradle_
```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/jarlehansen/maven"
    }
}
```

```groovy
compile('com.github.springfox.loader:springfox-loader:0.0.1')
```

### Maven

_pom.xml_
```xml
<repositories>
    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>bintray-jarlehansen-maven</id>
        <name>bintray</name>
        <url>http://dl.bintray.com/jarlehansen/maven</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.springfox.loader</groupId>
    <artifactId>springfox-loader</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

Add `@EnableSpringfox` to the class containing the Spring boot main method (`@SpringBootApplication`).
This will automatically create the springfox configuration for you.

It is also possible to add Spring placeholders (with the `${...}` syntax) as values in the EnableSpringfox-annotation.
This can be useful if you want to add values that are defined in for example properties/yml files.

 __Examples__
 ```java
@EnableSpringfox(
        @Info(title = "title", version = "${version}")
)
 ```

```java
@EnableSpringfox(@Info(
         title = "title",
         version = "version",
         description = "description",
         termsOfService = "termsOfService",
         contact = @Contact(name = "name", url = "url", email = "email"),
         license = @License(name = "name", url = "url"))
 )
```

### Properties

It is also possible to configure the values using properties/yml files (typically application.properties/application.yml).
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
After enabling Springfox Loader you can access the webpage: http://localhost:8080__/swagger-ui.html__

A list of the swagger resources are available here: http://localhost:8080__/swagger-resources__

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