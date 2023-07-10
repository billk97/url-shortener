package dev.konstantinou.urlshortener.utils;

import java.util.List;

public record JwtDetails(String subject, String issuer, List<String> permissions) {
}
