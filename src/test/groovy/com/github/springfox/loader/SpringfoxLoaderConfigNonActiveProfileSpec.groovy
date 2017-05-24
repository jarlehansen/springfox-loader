package com.github.springfox.loader

import com.github.springfox.loader.testutils.TestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import springfox.documentation.spring.web.plugins.Docket

@ActiveProfiles(["nospringfox"])
@SpringBootTest(classes = TestApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringfoxLoaderConfigNonActiveProfileSpec extends Specification {

    @Autowired(required = false)
    private Docket docket

    def "Initialize with test profile"() {
        when:
        def disabled = (docket == null)

        then:
        disabled
    }
}
