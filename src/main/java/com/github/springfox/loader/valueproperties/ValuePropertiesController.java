package com.github.springfox.loader.valueproperties;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/springfox-loader", method = RequestMethod.GET)
public class ValuePropertiesController {
    private ValuePropertiesLocator valuePropertiesLocator;

    public void setValuePropertiesLocator(ValuePropertiesLocator valuePropertiesLocator) {
        this.valuePropertiesLocator = valuePropertiesLocator;
    }

    @ApiOperation("List the Value-annotations (springfox-loader)")
    @RequestMapping(value = "/valueprops", method = RequestMethod.GET)
    public ResponseEntity<?> getValueProperties() {
        if (valuePropertiesLocator == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(valuePropertiesLocator.getProperties());
        }
    }
}
