package com.github.springfox.loader

import com.github.springfox.loader.testutils.TestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.EnvironmentTestUtils
import org.springframework.core.env.AbstractEnvironment
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import springfox.documentation.spring.web.plugins.Docket

@ActiveProfiles(["test", "dev"])
@SpringBootTest(classes = TestApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringfoxLoaderConfigSpec extends Specification {

    @Autowired
    private ConfigurableEnvironment environment

    @Autowired
    private SpringfoxLoaderProps loaderProps

    @Autowired(required = false)
    private Docket docket

    def "Initialize properties"() {
        when:
        def title = loaderProps.getTitle()
        def version = loaderProps.getVersion()

        then:
        title == "test"
        version == "1.0.0"
    }

    def "Initialize with test profile"() {
        when:
        def enabled = docket.isEnabled()

        then:
        enabled
    }

    def "Initialize with non-active profile"() {
        given:
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");

        when:
        def disabled = (docket == null)

        then:
        disabled
    }
}
