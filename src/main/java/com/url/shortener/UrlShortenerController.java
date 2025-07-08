package com.url.shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(final UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam("url") String originalUrl) {
        String shortUrl = service.shortenUrl(originalUrl);
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Object> redirect(@PathVariable("key") String key) {
        String originalUrl = service.getLongUrl(key);

        if (originalUrl == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create(originalUrl)).build();
    }
}
