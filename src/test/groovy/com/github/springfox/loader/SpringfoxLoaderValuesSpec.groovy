package com.github.springfox.loader

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import spock.lang.Specification

@SpringApplicationConfiguration(classes = TestApplication.class)
@WebIntegrationTest
class SpringfoxLoaderValuesSpec extends Specification {

    @Autowired
    private SpringfoxLoaderValues values

    def "Values"() {
        given:
        System.setProperty("springfox.name", "test-app")

        when:
        values.init()

        then:
        values != null
    }
}
