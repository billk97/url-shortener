package dev.konstantinou.urlshortener.config.security;

import dev.konstantinou.urlshortener.config.security.JwtAuthorizationFilter;
import dev.konstantinou.urlshortener.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class WebSecurityConfig {

    //todo take from application.yaml
    private static final String API_URL = "/api";
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ApplicationUserRepository applicationUserRepo;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .headers(a -> a.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.OPTIONS, API_URL + "/**").permitAll()                )
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET,
                                        API_URL + "/*",
                                        API_URL + "/health")
                                .permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST,
                        API_URL + "/shorten",
                        API_URL + "/admin/login").
                        permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.PUT, API_URL).permitAll())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(userDetailsService());
        return dao;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
            applicationUserRepo
                    .findApplicationUserByUsername(username)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("Username or password are incorrect")
                    );
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }


}
