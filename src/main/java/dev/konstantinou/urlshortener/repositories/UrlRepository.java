package dev.konstantinou.urlshortener.repositories;

import dev.konstantinou.urlshortener.apihelpers.BaseRepository;
import dev.konstantinou.urlshortener.entities.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UrlRepository extends BaseRepository<Url, Integer>, PagingAndSortingRepository<Url, Integer> {

    Url findByShortUrl(String shortUrl);
    Url findByLongUrl(String longUrl);

    Page<Url> findAll(Pageable pageable);

}
