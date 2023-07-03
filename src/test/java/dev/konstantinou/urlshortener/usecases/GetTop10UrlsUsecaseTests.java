package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.entities.MetaData;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.entities.UrlStats;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.HashSet;

@DataJpaTest(showSql = false, properties = "spring.profiles.active:test")
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GetTop10UrlsUsecaseTests {

    @Autowired
    private UrlRepository urlRepo;
    @Autowired
    private UrlStatsRepository urlStatsRepo;


    private GetTop10UrlsUsecase getTop10UrlsUsecase;
    @BeforeEach
    void init() {


        var urlOne = new Url("long/url-one");
        urlRepo.save(urlOne);
        urlRepo.save(new Url("long/url-two"));
        urlRepo.save(new Url("long/url-three"));
        var metadataList = new HashSet<MetaData>();
        metadataList.add(new MetaData("IP", Instant.now(), "Chrome"));
        var stats = new UrlStats(urlOne, metadataList);
        urlStatsRepo.save(stats);

        getTop10UrlsUsecase = new GetTop10UrlsUsecase(urlStatsRepo);
    }



    @Test
    void given_only_one_url_it_should_return_only_one() {
        var response = getTop10UrlsUsecase.query();
        assertEquals(response.size(), 1);
        assertEquals(response.iterator().next().url().getLongUrl(), "long/url-one");
    }

}