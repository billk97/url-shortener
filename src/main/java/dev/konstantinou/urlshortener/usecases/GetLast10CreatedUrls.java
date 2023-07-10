package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.repositories.UrlRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetLast10CreatedUrls {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<Url> query() {
        List<Url> results = entityManager
                .createQuery("SELECT u FROM Url u order by u.createdAt limit 10")
                .getResultList();
        return results;
    }

}
