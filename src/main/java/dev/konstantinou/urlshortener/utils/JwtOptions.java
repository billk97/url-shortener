package dev.konstantinou.urlshortener.utils;

import java.security.KeyPair;
import java.time.Instant;
import java.util.List;

public record JwtOptions(
        String id,
        String issuer,
        Instant createdAt,
        int minutesUntilExpiration,
        String subject,
        List<String> permissions) {
}
