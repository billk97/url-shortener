package dev.konstantinou.urlshortener.config.security;

import java.time.Instant;

public record AuthToken(String token, String refreshToken, Instant refreshTokenExpirationTime) {
}
