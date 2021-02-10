package com.example.greetings.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
public class GreetingsController {

    @GetMapping("/greet/{name}")
    public ResponseEntity redirectToHello(@PathVariable("name") String name) {
        final URI redirectURL = UriComponentsBuilder
                .newInstance()
                .path("/hello/" + name)
                .build()
                .toUri();

        CacheControl cacheControl = CacheControl.maxAge(2L, SECONDS);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(redirectURL);
        responseHeaders.setCacheControl(cacheControl.getHeaderValue());

        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "Hello " + name;
    }
}
