package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.config.security.UserCredentials;
import dev.konstantinou.urlshortener.repositories.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
class CreateAdminUserTests {

    @Autowired
    private ApplicationUserRepository applicationUserRepo;
    private CreateAdminUser createAdminUser;
    @BeforeEach
    void init() {
        createAdminUser =  new CreateAdminUser(applicationUserRepo);
    }

    @Test
    void given_a_set_of_credentials_a_user_should_be_created() {
        var user = createAdminUser.command(new UserCredentials("bill", "secure"));
        assertEquals(applicationUserRepo.findAll().size(), 1);
        assertEquals(applicationUserRepo.findApplicationUserByUsername("bill").get().getUsername(), "bill");

    }

}