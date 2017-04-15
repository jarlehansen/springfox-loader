package com.github.springfox.loader.valueproperties

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ValuePropertiesControllerSpec extends Specification {
    private ValuePropertiesController controller
    private ValuePropertiesLocator valuePropertiesLocator
    private MockMvc mockMvc

    void setup() {
        valuePropertiesLocator = Mock(ValuePropertiesLocator)
        controller = new ValuePropertiesController(valuePropertiesLocator: valuePropertiesLocator)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "Return 404 Not Found when ValuePropertiesLocator is null"() {
        given:
        controller.setValuePropertiesLocator(null)

        when:
        def response = mockMvc.perform(get('/springfox-loader/valueprops'))

        then:
        response.andExpect(status().isNotFound())
    }

    def "Return value properties when ValuePropertiesLocator is set"() {
        when:
        def response = mockMvc.perform(get('/springfox-loader/valueprops'))

        then:
        1 * valuePropertiesLocator.getProperties() >> [new ValueProperty('${test-key:test-value}')]
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].key').value(equalTo('test-key')))
                .andExpect(jsonPath('$[0].defaultValue').value(equalTo('test-value')))
    }
}
