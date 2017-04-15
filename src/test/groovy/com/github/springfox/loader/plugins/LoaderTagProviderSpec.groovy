package com.github.springfox.loader.plugins

import spock.lang.Specification
import springfox.documentation.spi.service.contexts.OperationContext

class LoaderTagProviderSpec extends Specification {
    private OperationContext context

    void setup() {
        context = Mock(OperationContext) {
            getGroupName() >> 'test-controller'
        }
    }

    def "Initialize LoaderTagProvider with convenience mode"() {
        given:
        def loaderTagProvider = new LoaderTagProvider(true)

        when:
        def tags = loaderTagProvider.tags(context)

        then:
        tags[0] == 'Test'
    }

    def "Initialize LoaderTagProvider with convenience mode disabled"() {
        given:
        def loaderTagProvider = new LoaderTagProvider(false)

        when:
        def tags = loaderTagProvider.tags(context)

        then:
        tags.size() == 1
    }
}
