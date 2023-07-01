package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.dtos.CreateShortUrlRequestDTO;
import dev.konstantinou.urlshortener.dtos.ShortUrlResponseDTO;
import dev.konstantinou.urlshortener.entities.MetaData;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
import dev.konstantinou.urlshortener.usecases.Redirect;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api-prefix}/")
public class ApiController {

    private final UrlRepository urlRepo;
    private final UrlStatsRepository urlStatsRepo;

    private final Redirect redirect;
    @PostMapping("shorten")
    public ShortUrlResponseDTO createShortUrl(@RequestBody CreateShortUrlRequestDTO dto) {
        var existingUrl = urlRepo.findByLongUrl(dto.longUrl());
        if (existingUrl != null) {
            return new ShortUrlResponseDTO(existingUrl.getFullShortUrl(), existingUrl.getLongUrl());
        }
        var url = urlRepo.save(new Url(dto.longUrl()));
        return new ShortUrlResponseDTO(url.getFullShortUrl(), url.getLongUrl());
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<String> redirect(@PathVariable String shortUrl, HttpServletRequest request) {
        var url = redirect.command(shortUrl, request.getRemoteAddr());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", url.getLongUrl());
        return ResponseEntity.status(Response.SC_MOVED_PERMANENTLY).headers(headers).build();
    }



}
