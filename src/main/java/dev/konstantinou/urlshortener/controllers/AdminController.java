package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.config.security.AuthToken;
import dev.konstantinou.urlshortener.config.security.UserCredentials;
import dev.konstantinou.urlshortener.entities.ApplicationUser;
import dev.konstantinou.urlshortener.usecases.CreateAdminUser;
import dev.konstantinou.urlshortener.usecases.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api-prefix}/admin")
public class AdminController {

    private final Login login;
    private final CreateAdminUser createAdminUser;

    @PostMapping("/login")
    public AuthToken login(@RequestBody UserCredentials credentialSet) {
        return login.command(credentialSet);
    }

    @PostMapping("/create")
    public ApplicationUser createUser(@RequestBody UserCredentials userCredentials) {
        return createAdminUser.command(userCredentials);
    }


}
