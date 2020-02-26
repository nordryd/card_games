package com.nordryd.cardgames.controller;

import static java.lang.String.format;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cardgame")
public class TestController
{
    @GetMapping("/{name}")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
        return format("%s has hit this api. Someone call child services.", name);
    }
}
