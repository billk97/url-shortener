package dev.konstantinou.urlshortener.config.security;

import dev.konstantinou.urlshortener.entities.ApplicationUser;
import dev.konstantinou.urlshortener.utils.JwtDetails;
import dev.konstantinou.urlshortener.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${secrets.privateKey}")
    private  String privateKey;
    @Value(("${secrets.publicKey}"))
    private String publicKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!token.contains("Bearer")) {
            throw new BadCredentialsException("Invalid credentials");
        }
        token = token.replace("Bearer ", "");
        JwtDetails details = JwtUtils.getValidatedJwtDetails(token, "secret");
        ApplicationUser user = new ApplicationUser(details.subject(), details.permissions());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities() ));
        filterChain.doFilter(request, response);
    }
}
