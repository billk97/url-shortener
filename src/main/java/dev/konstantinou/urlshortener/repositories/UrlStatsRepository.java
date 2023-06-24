package dev.konstantinou.urlshortener.repositories;

import dev.konstantinou.urlshortener.apihelpers.BaseRepository;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;

public interface UrlStatsRepository extends BaseRepository<UrlStats, Integer> {
    UrlStats findByUrl(Url url);
}
