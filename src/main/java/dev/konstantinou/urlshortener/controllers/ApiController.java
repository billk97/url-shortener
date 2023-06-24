package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.dtos.CreateShortUrlRequestDTO;
import dev.konstantinou.urlshortener.dtos.ShortUrlResponseDTO;
import dev.konstantinou.urlshortener.entities.MetaData;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
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
        var url = urlRepo.findByShortUrl(shortUrl);
        if (url == null) {
            throw new IllegalArgumentException("Given url was not found");
        }
        var stats = urlStatsRepo.findByUrl(url);
        if (stats == null) {
            UrlStats urlStats = new UrlStats();
            urlStats.setUrl(url);
            List<MetaData> metaDataList = new ArrayList<>();
            var metaData = new MetaData();
            metaData.setIpAddress(request.getRemoteAddr());
            metaData.setTimestamp(Instant.now());
            metaData.setBrowser("Chrome");
            metaDataList.add(metaData);
            urlStats.setMetaDataList(metaDataList);
            urlStatsRepo.save(urlStats);
        } else {
            var metaData = new MetaData();
            metaData.setIpAddress(request.getRemoteAddr());
            metaData.setTimestamp(Instant.now());
            metaData.setBrowser("Chrome");
            stats.getMetaDataList().add(metaData);
            urlStatsRepo.save(stats);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", url.getLongUrl());
        return ResponseEntity.status(Response.SC_MOVED_PERMANENTLY).headers(headers).build();
    }



}
