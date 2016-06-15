package com.github.springfox.loader

import io.swagger.annotations.Contact
import io.swagger.annotations.Info
import org.springframework.context.ApplicationContext
import org.springframework.util.StringValueResolver
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
        def infoAnnotation = Mock(Info)
        def contactAnnotation = Mock(Contact)
        def springfoxLoader = new SpringfoxLoader(loaderProps: loaderProps, annotation: annotation)

        when:
        def contact = springfoxLoader.getContact()

        then:
        1 * annotation.value() >> infoAnnotation
        1 * infoAnnotation.contact() >> contactAnnotation

        1 * contactAnnotation.name() >> ""
        1 * loaderProps.getContactName() >> "contact-name"
        1 * contactAnnotation.url() >> ""
        1 * loaderProps.getContactUrl() >> "contact-url"
        1 * contactAnnotation.email() >> "contact-email"
        1 * loaderProps.getContactEmail() >> ""

        contact.name == "contact-name"
        contact.url == "contact-url"
        contact.email == "contact-email"
    }

    def "Get value, @Value property"() {
        given:
        def stringValueResolver = Mock(StringValueResolver)
        def springfoxLoader = new SpringfoxLoader(stringValueResolver: stringValueResolver)

        when:
        def val = springfoxLoader.val('${test}', "")

        then:
        1 * stringValueResolver.resolveStringValue(_ as String) >> "test"
        val == "test"
    }

    def "Get value, standard value"() {
        given:
        def springfoxLoader = new SpringfoxLoader()

        when:
        def val = springfoxLoader.val("test", "")

        then:
        val == "test"
    }
}
