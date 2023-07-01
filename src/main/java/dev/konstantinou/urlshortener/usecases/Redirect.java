package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.entities.MetaData;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Component
public class Redirect {

    private final UrlRepository urlRepo;
    private final UrlStatsRepository urlStatsRepo;


    public Url command(String shortUrl, String ipAddress) {
        var url = urlRepo.findByShortUrl(shortUrl);
        if (url == null) {
            throw new IllegalArgumentException("Given url was not found");
        }
        saveStatistics(url, ipAddress);
        return url;
    }

    @Async
    public void saveStatistics(Url url, String ipAddress) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var stats = urlStatsRepo.findByUrl(url);
        if (stats == null) {
            stats = new UrlStats();
            stats.setUrl(url);
            stats.setMetaDataList(new HashSet<>());
        }
        stats.getMetaDataList().add(new MetaData(ipAddress, Instant.now(), "Chrome"));
        urlStatsRepo.save(stats);
    }

}
