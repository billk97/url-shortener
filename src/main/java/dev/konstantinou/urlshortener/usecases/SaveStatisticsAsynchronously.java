package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.entities.MetaData;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
@Transactional
public class SaveStatisticsAsynchronously {

    private final UrlStatsRepository urlStatsRepo;

    @Async
    public void command(Url url, String ipAddress) {
        var stats = urlStatsRepo.findByUrl(url);
        if (stats == null) {
            stats = new UrlStats();
            stats.setUrl(url);
            stats.setMetaDataList(new HashSet<>());
        }
        stats.addMetaData(new MetaData(ipAddress, Instant.now(), "Chrome"));
        urlStatsRepo.save(stats);
    }

}
