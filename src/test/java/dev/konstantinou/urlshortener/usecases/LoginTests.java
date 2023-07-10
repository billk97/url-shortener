package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.config.security.AuthToken;
import dev.konstantinou.urlshortener.config.security.UserCredentials;
import dev.konstantinou.urlshortener.config.security.WebSecurityConfig;
import dev.konstantinou.urlshortener.entities.ApplicationUser;
import dev.konstantinou.urlshortener.enums.Privileges;
import dev.konstantinou.urlshortener.repositories.ApplicationUserRepository;
import dev.konstantinou.urlshortener.utils.JwtUtils;
import groovy.xml.StreamingDOMBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

@SpringBootTest()
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
class LoginTests {


    @Autowired
    private ApplicationUserRepository applicationUserRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CreateAdminUser createAdminUser;
    private Login login;

    private final String JWT_SECRET = "secret";

    private ApplicationUser applicationUser;

    @BeforeEach
    void init() {
        var priv = new HashSet<Privileges>();
        priv.add(Privileges.CAN_VIEW_STATS);
        applicationUser = createAdminUser.command(new UserCredentials("admin", "password"));
        applicationUser.setUserPrivileges(priv);
        applicationUser.setEnabled(true);
        applicationUser.setAccountNonExpired(true);
        applicationUser.setCredentialsNonExpired(true);
        applicationUser.setAccountNonLocked(true);
        applicationUserRepo.save(applicationUser);
        login = new Login(applicationUserRepo, authenticationManager);
    }


    @AfterEach
    void tireDown() {
        applicationUserRepo.deleteAll();
    }


    @Test
    void given_an_exiting_set_off_username_and_password_login_should_be_successful() {
        String username = "admin";
        String password = "password";
        AuthToken authToken = login.command(new UserCredentials(username, password));;
        var JWT = JwtUtils.validateSymmetricJwt(authToken.token(), JWT_SECRET);
        assertEquals(JWT.getSubject(), username);
    }


    @Test
    void given_an_exiting_set_off_username_and_password_login_should_contain_the_correct_claims() {
        String username = "admin";
        String password = "password";
        AuthToken authToken = login.command(new UserCredentials(username, password));;
        var JWT = JwtUtils.validateSymmetricJwt(authToken.token(), JWT_SECRET);
        assertEquals(JWT.getSubject(), username);
        assertEquals(JwtUtils.getValidatedJwtDetails(authToken.token(), JWT_SECRET).permissions().get(0), Privileges.CAN_VIEW_STATS.toString());
    }

    @Test
    void given_a_non_existing_username_login_should_throw_bad_credentials_exception() {
        String username = "bill";
        String password = "password";
        assertThrows(BadCredentialsException.class, () -> {
            login.command(new UserCredentials(username, password));
        });
    }

    @Test
    void given_a_invalid_password_login_throw_bad_credentials_exception() {
        String username = "admin";
        String password = "wrong-pass-frase";
        assertThrows(BadCredentialsException.class, () -> {
            login.command(new UserCredentials(username, password));
        });
    }

    @Test
    void given_a_valid_set_of_credentials_IT_should_throw_if_account_is_locked(){
        applicationUser.setAccountNonLocked(false);
        applicationUserRepo.save(applicationUser);
        String username = "admin";
        String password = "wrong-pass-frase";
        assertThrows(LockedException.class, () -> {
            login.command(new UserCredentials(username, password));
        });

    }

    @Test
    void given_a_valid_set_of_credentials_IT_should_throw_if_account_is_not_Enabled(){
        applicationUser.setEnabled(false);
        applicationUserRepo.save(applicationUser);
        String username = "admin";
        String password = "wrong-pass-frase";
        assertThrows(DisabledException.class, () -> {
            login.command(new UserCredentials(username, password));
        });
    }

    @Test
    void given_a_valid_set_of_credentials_IT_should_throw_if_account_is_expired(){
        applicationUser.setAccountNonExpired(false);
        applicationUserRepo.save(applicationUser);
        String username = "admin";
        String password = "wrong-pass-frase";
        assertThrows(AccountExpiredException.class, () -> {
            login.command(new UserCredentials(username, password));
        });

    }

    @Test
    void given_a_valid_set_of_credentials_IT_should_throw_if_credentials_have_expired() {
        applicationUser.setCredentialsNonExpired(false);
        applicationUserRepo.save(applicationUser);
        String username = "admin";
        String password = "wrong-pass-frase";
        assertThrows(BadCredentialsException.class, () -> {
            login.command(new UserCredentials(username, password));
        });

    }



}