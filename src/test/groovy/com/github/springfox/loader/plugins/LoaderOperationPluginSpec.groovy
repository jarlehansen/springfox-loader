package com.github.springfox.loader.plugins

import spock.lang.Specification
import springfox.documentation.builders.OperationBuilder
import springfox.documentation.service.Operation
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.OperationContext

class LoaderOperationPluginSpec extends Specification {
    private OperationContext context
    private OperationBuilder builder

    void setup() {
        builder = Mock(OperationBuilder) {
            build() >> Mock(Operation) {
                getSummary() >> 'MyTest'
            }
        }
        context = Mock(OperationContext) {
            operationBuilder() >> builder
        }
    }

    def "Update summary when creating LoaderOperationPlugin with convention enabled"() {
        when:
        def plugin = new LoaderOperationPlugin(true)
        plugin.apply(context)

        then:
        1 * builder.summary('My test')
    }

    def "Do not update summary when creating LoaderOperationPlugin with convention disabled"() {
        when:
        def plugin = new LoaderOperationPlugin(false)
        plugin.apply(context)

        then:
        0 * builder.summary(_ as String)
    }

    def "Return true on supports swagger 2 documentation type"() {
        given:
        def plugin = new LoaderOperationPlugin(true)

        when:
        def supports = plugin.supports(DocumentationType.SWAGGER_2)

        then:
        supports
    }

    def "Return false on supports swagger 1.2 documentation type"() {
        given:
        def plugin = new LoaderOperationPlugin(true)

        when:
        def supports = plugin.supports(DocumentationType.SWAGGER_12)

        then:
        !supports
    }
}
