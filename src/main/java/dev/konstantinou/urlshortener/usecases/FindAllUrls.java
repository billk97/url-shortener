package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllUrls {

    private final UrlRepository urlRepo;

    public Page<Url> query(Pageable p) {
        return urlRepo.findAll(p);
    }
}
