package dev.konstantinou.urlshortener.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.servlet.http.PushBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ipAddress;
    private Instant timestamp;
    private String browser;

    public MetaData(String ipAddress, Instant timestamp, String browser) {
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
        this.browser = browser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaData metaData = (MetaData) o;
        return id == metaData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
