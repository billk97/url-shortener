package dev.konstantinou.urlshortener.dtos;

import dev.konstantinou.urlshortener.entities.Url;

public record TopTenDTO (Url url, int visits){
}
