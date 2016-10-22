package com.github.springfox.loader.valueproperties

import com.github.springfox.loader.testutils.TestConfig
import spock.lang.Specification

class ValuePropertiesLocatorSpec extends Specification {

    def "Get properties"() {
        given:
        def propertiesLocator = new ValuePropertiesLocator(TestConfig.class.getPackage().getName())

        when:
        def properties = propertiesLocator.getProperties()

        then:
        properties[0].key == "test.property"
        properties[0].defaultValue == "test123"
    }
}
