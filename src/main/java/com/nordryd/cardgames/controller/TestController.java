package com.nordryd.cardgames.controller;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cardgames")
public class TestController
{
    private final TestService service;

    @Autowired
    public TestController(final TestService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    @ResponseStatus(OK)
    @ResponseBody
    public String greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
        return format("The controller says hello. %s", service.testPrint(name));
    }

    @GetMapping("/{left}/{right}")
    @ResponseStatus(OK)
    @ResponseBody
    public int add(@RequestParam(value = "left") final int left, @RequestParam(value = "right") final int right) {
        return service.add(left, right);
    }
}
