package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.dtos.TopTenDTO;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.usecases.FindAllUrls;
import dev.konstantinou.urlshortener.usecases.GetLast10CreatedUrls;
import dev.konstantinou.urlshortener.usecases.GetTop10UrlsUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${server.api-prefix}/stats")
@RequiredArgsConstructor
public class StatsController {

    private final GetTop10UrlsUsecase getTop10UrlsUsecase;
    private final GetLast10CreatedUrls getLast10CreatedUrls;
    private final FindAllUrls findAllUrls;

    @GetMapping("/top-ten-urls")
    public Set<TopTenDTO> getTopTenUrls() {
        return getTop10UrlsUsecase.query();
    }

    @GetMapping("/last-ten-urls")
    public List<Url> getLastTenUrls() {
        return getLast10CreatedUrls.query();
    }

    @GetMapping("/urls")
    public Page<Url> getAllPageable(@RequestParam int page, @RequestParam int size) {
        return findAllUrls.query(PageRequest.of(page, size));
    }

}
