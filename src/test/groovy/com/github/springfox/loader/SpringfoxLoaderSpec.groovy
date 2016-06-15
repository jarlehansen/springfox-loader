package com.github.springfox.loader

import org.springframework.context.ApplicationContext
import spock.lang.Specification

class SpringfoxLoaderSpec extends Specification {

    def "No @EnableSpringfox annotations found"() {
        given:
        def applicationContext = Mock(ApplicationContext)
        def springfoxLoader = new SpringfoxLoader(loaderProps: new SpringfoxLoaderProps(), annotation: Mock(EnableSpringfox))

        when:
        springfoxLoader.getAnnotation(applicationContext)

        then:
        1 * applicationContext.getBeansWithAnnotation(_ as Class) >> [:]
        thrown IllegalStateException
    }

    def "Get contact"() {
        given:
        def loaderProps = Mock(SpringfoxLoaderProps)
        def annotation = Mock(EnableSpringfox)
        def springfoxLoader = new SpringfoxLoader(loaderProps: loaderProps, annotation: annotation)

        when:
        def contact = springfoxLoader.getContact()

        then:
        1 * annotation.contactName() >> "contact-name"
        1 * annotation.contactUrl() >> "contact-url"
        1 * annotation.contactEmail() >> ""
        1 * loaderProps.getContactEmail() >> "contact-email"

        contact.name == "contact-name"
        contact.url == "contact-url"
        contact.email == "contact-email"
    }
}
