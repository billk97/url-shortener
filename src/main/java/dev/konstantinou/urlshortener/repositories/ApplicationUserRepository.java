package dev.konstantinou.urlshortener.repositories;

import dev.konstantinou.urlshortener.apihelpers.BaseRepository;
import dev.konstantinou.urlshortener.entities.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends BaseRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
