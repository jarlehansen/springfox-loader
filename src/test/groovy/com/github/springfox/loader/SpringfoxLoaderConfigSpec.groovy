package com.github.springfox.loader

import com.github.springfox.loader.testutils.TestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes =  TestApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringfoxLoaderConfigSpec extends Specification {

    @Autowired
    private SpringfoxLoaderProps loaderProps

    def "Initialize properties"() {
        when:
        def title = loaderProps.getTitle()
        def version = loaderProps.getVersion()

        then:
        title == "test"
        version == "1.0.0"
    }
}
