package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.entities.MetaData;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Component
@EnableAsync
public class Redirect {

    private final UrlRepository urlRepo;
    private final SaveStatisticsAsynchronously saveStatistics;
    public Url command(String shortUrl, String ipAddress) {
        var url = urlRepo.findByShortUrl(shortUrl);
        if (url == null) {
            throw new IllegalArgumentException("Given url was not found");
        }
        saveStatistics.command(url, ipAddress);
        return url;
    }



}
