package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.dtos.TopTenDTO;
import dev.konstantinou.urlshortener.entities.Url;
import dev.konstantinou.urlshortener.repositories.UrlStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetTop10UrlsUsecase {

    private final UrlStatsRepository urlStatsRepo;
    public Set<TopTenDTO> query() {
        Set<TopTenDTO> response;
        List<Object[]> stats = urlStatsRepo.getTop10Urls();
        response = stats
                .stream().
                map(s -> new TopTenDTO((Url) s[0], (Integer) s[1]))
                .collect(Collectors.toSet());;
        return response;
    }
}
