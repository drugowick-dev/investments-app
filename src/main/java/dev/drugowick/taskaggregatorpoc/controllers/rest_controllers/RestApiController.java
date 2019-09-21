package dev.drugowick.taskaggregatorpoc.controllers.rest_controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class RestApiController {

    @Data
    @AllArgsConstructor
    private class ExampleResponseClass {
        private int integer;
        private String text;
        private Long longNumber;
    }

    @RequestMapping(value = "/rest/hello", method = RequestMethod.GET)
    public String hello() {
        String responseToBeParsedToJson = "Hello, rest requester. Thanks for hitting on me! =)";
        return responseToBeParsedToJson;
    }

    @RequestMapping(value = "/rest/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<ExampleResponseClass> listOfItems() {
        Set<ExampleResponseClass> listOfItems = new HashSet<>();
        for (int i = 1; i <= 10; i++) {
            ExampleResponseClass exampleResponseClass = new ExampleResponseClass(i, "Iteration " + i, Integer.toUnsignedLong(i));
            listOfItems.add(exampleResponseClass);
        }
        return listOfItems;
    }

}
