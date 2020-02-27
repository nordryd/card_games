package com.nordryd.cardgames.controller;

import static java.lang.String.format;

import org.springframework.stereotype.Service;

@Service
public class TestService
{
    public String testPrint(final String param) {
        return format("So does the service! Welcome, %s!", param);
    }

    public int add(final int left, final int right) {
        return left + right;
    }
}
