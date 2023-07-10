package dev.konstantinou.urlshortener.usecases;

import dev.konstantinou.urlshortener.config.security.AuthToken;
import dev.konstantinou.urlshortener.config.security.UserCredentials;
import dev.konstantinou.urlshortener.repositories.ApplicationUserRepository;
import dev.konstantinou.urlshortener.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Login {

    private final ApplicationUserRepository applicationUserRepo;
    private final AuthenticationManager authenticationManager;

    public AuthToken command(UserCredentials userCredentials) {
        if (!applicationUserRepo.findApplicationUserByUsername(userCredentials.username()).isPresent()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userCredentials.username(),
                                userCredentials.password()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthToken(JwtUtils.createDefaultSymmetricJwt(
                authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                "secret"),  null, null);
    }

}
