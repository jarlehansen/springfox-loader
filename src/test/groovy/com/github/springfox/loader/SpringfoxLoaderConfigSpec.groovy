package com.github.springfox.loader

import com.github.springfox.loader.testutils.TestApplication
import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.http.HttpStatus
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

    @Autowired
    private SpringfoxLoaderConfig springfoxLoaderConfig

    @Autowired(required = false)
    private Docket docket

    @Autowired
    private TestRestTemplate restTemplate

    def "Initialize properties"() {
        when:
        def title = loaderProps.getTitle()
        def version = loaderProps.getVersion()

        then:
        title == 'test'
        version == '1.0.0'
    }

    def "Initialize with test profile"() {
        when:
        def enabled = docket.isEnabled()

        then:
        enabled
    }

    def "GET api-docs with vendor extension"() {
        when:
        def response = restTemplate.getForEntity('/v2/api-docs', String)
        def json = response.getBody()

        then:
        response.statusCode == HttpStatus.OK
        JsonPath.read(json, '$.info.x-test.test-key') == 'test-value'
    }

    def "Custom base path for swagger-ui"() {
        when:
        def response = restTemplate.getForEntity('/docs/swagger-ui.html', String)

        then:
        response.statusCode == HttpStatus.OK
    }
}
