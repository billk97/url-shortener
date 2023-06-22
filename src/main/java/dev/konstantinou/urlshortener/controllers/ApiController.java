package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.dtos.CreateShortUrlRequestDTO;
import dev.konstantinou.urlshortener.dtos.ShortUrlResponseDTO;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api-prefix}/")
public class ApiController {

    private final UrlRepository urlRepo;
    @PostMapping("create")
    public ShortUrlResponseDTO createShortUrl(@RequestBody CreateShortUrlRequestDTO dto) {
        var url = urlRepo.save(new Url(dto.longUrl()));
        return new ShortUrlResponseDTO(url.getShortUrl(), url.getLongUrl());
    }



}
