package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.dtos.TopTenDTO;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.usecases.GetTop10UrlsUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${server.api-prefix}/stats")
@RequiredArgsConstructor
public class StatsController {

    private final GetTop10UrlsUsecase getTop10UrlsUsecase;
    @GetMapping("/top-ten-urls")
    public Set<TopTenDTO> getTopTenUrls() {
        return getTop10UrlsUsecase.query();
    }

    @GetMapping("/last-ten-urls")
    public List<UrlStats> getLastTenUrls(Object obj) {
        return null;
    }
}
