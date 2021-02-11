package com.example.greetings.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
public class FarewellController {

    @GetMapping("/farewell/{name}")
    public ResponseEntity redirectToBye(HttpServletRequest request, @PathVariable("name") String name) {
        final URI redirectURL = UriComponentsBuilder
                .newInstance()
                .path(getForwardedPrefix(request).concat("/bye/") + name)
                .build()
                .toUri();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(redirectURL);

        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/bye/{name}")
    public String sayBye(@PathVariable("name") String name) {
        return "Bye " + name;
    }

    private String getForwardedPrefix(HttpServletRequest request) {
        return isNull(request.getHeader("X-Forwarded-Prefix")) ? "" : request.getHeader("X-Forwarded-Prefix");
    }

    @GetMapping("/go-to-google")
    public ResponseEntity redirectToBye() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("https://www.google.com/"));
        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);
    }
}
