package dev.konstantinou.urlshortener.repositories;

import dev.konstantinou.urlshortener.apihelpers.BaseRepository;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UrlStatsRepository extends BaseRepository<UrlStats, Integer> {


    UrlStats findByUrl(Url url);

    @Query(value = "select us.url, count(us.metaDataList) from UrlStats us group by us.url order by count (us.metaDataList) desc limit 10")
    List<Object[]> getTop10Urls();



}
