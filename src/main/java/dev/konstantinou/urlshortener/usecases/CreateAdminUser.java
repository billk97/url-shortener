package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.config.security.UserCredentials;
import dev.konstantinou.urlshortener.entities.ApplicationUser;
import dev.konstantinou.urlshortener.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAdminUser {

    private final ApplicationUserRepository applicationUserRepo;
    public ApplicationUser command(UserCredentials userCredentials) {
       var existingUser =  applicationUserRepo.findApplicationUserByUsername(userCredentials.username());
       if (existingUser.isPresent()) {
           throw new IllegalArgumentException("User already exist");
       }

       ApplicationUser newUser = ApplicationUser.CreateNewApplicationUser (userCredentials.username(), userCredentials.password());
       return applicationUserRepo.save(newUser);

    }

}
