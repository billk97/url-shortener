package dev.konstantinou.urlshortener.dtos;

import java.time.Instant;

public record ApiError (Instant timestamp, String error, String details){
}
