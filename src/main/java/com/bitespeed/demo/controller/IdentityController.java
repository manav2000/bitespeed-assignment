package com.bitespeed.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/identity")
public class IdentityController {

    @GetMapping
    public Object getContact() {

        return null;
    }

    @PostMapping
    public Object createContact(@RequestBody Object req) {

        return null;
    }
}
