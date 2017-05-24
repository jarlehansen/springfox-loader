package com.github.springfox.loader.controller

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SpringfoxLoaderControllerSpec extends Specification {
    private SpringfoxLoaderController controller
    private RestTemplate restTemplate
    private MockMvc mockMvc

    void setup() {
        restTemplate = Mock(RestTemplate)
        controller = new SpringfoxLoaderController(restTemplate: restTemplate)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "GET api-docs"() {
        when:
        def response = mockMvc.perform(get('/springfox-loader/api-docs'))

        then:
        1 * restTemplate.exchange('http://localhost/v2/api-docs', HttpMethod.GET, _ as HttpEntity, String) >> ResponseEntity.ok('')
        response.andExpect(status().isOk())
    }

    def "GET api-docs and skip tags"() {
        when:
        def response = mockMvc.perform(get('/springfox-loader/api-docs').param('skipTags', 'true'))

        then:
        1 * restTemplate.exchange('http://localhost/v2/api-docs', HttpMethod.GET, _ as HttpEntity, String) >> ResponseEntity.ok('{ "tags": [ "test123", "test234" ] }')
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.tags', hasSize(0)))
    }
}
