package com.github.springfox.loader

import spock.lang.Specification

class SpringfoxLoaderPropsSpec extends Specification {

    def "Get title with spring.application.name"() {
        given:
        def loaderProps = new SpringfoxLoaderProps(name: "name", title: "title")

        when:
        def title = loaderProps.getTitle()

        then:
        title == "name"
    }

    def "Get title with springfox.title"() {
        given:
        def loaderProps = new SpringfoxLoaderProps(title: "title")

        when:
        def title = loaderProps.getTitle()

        then:
        title == "title"
    }
}
