package com.github.springfox.loader.valueproperties

import spock.lang.Specification

class ValuePropertySpec extends Specification {

    def "Get key and default value from annotation value"() {
        given:
        def annotationValue = '${my.test:defaultValue}'

        when:
        def property = new ValueProperty(annotationValue)

        then:
        property.key == 'my.test'
        property.defaultValue == 'defaultValue'
    }

    def "Get key from annotation value"() {
        given:
        def annotationValue = '${my.test}'

        when:
        def property = new ValueProperty(annotationValue)

        then:
        property.key == 'my.test'
        property.defaultValue == null
    }
}
