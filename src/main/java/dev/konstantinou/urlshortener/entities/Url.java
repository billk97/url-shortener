package dev.konstantinou.urlshortener.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.zip.CRC32;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String shortUrl;
    @Column(unique = true, nullable = false)
    private String longUrl;
    @CreatedDate
    private Instant createdAt;
    public Url() {}
    public Url(String longUrl) {
        this.longUrl = longUrl;
        this.shortUrl = calculateSortUrl(longUrl);
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return this.shortUrl;
    }

    public String getFullShortUrl() {
        String serverUrl = "http://localhost:5000/api/";
        return serverUrl + this.shortUrl;
    }
    public String getLongUrl() {
        return this.longUrl;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public String calculateSortUrl(String longUrl) {
        if (longUrl == null) {
            throw new IllegalArgumentException("Url cannot be null");
        }
        CRC32 crc32 = new CRC32();
        crc32.update(longUrl.getBytes());
        return Long.toHexString(crc32.getValue());
    }
}
