package dev.konstantinou.urlshortener.repositories;

import dev.konstantinou.urlshortener.apihelpers.BaseRepository;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlStatsRepository extends BaseRepository<UrlStats, Integer> {


    UrlStats findByUrl(Url url);

    @Query(value = "select u.id, u.short_url, u.long_url, u.created_at, count(*), url_stats_id FROM url_stats_meta_data_list l \n" +
            "inner join url_stats us on us.id = l.url_stats_id\n" +
            "inner join url u on u.id = us.url_id\n" +
            "group by url_stats_id\n" +
            "order by count(*) desc limit 10", nativeQuery = true)
    List<Object[]> getTop10Urls();




}
