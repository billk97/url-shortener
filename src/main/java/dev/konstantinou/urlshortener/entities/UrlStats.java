package dev.konstantinou.urlshortener.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Url url;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private Set<MetaData> metaDataList;

    public UrlStats(Url url, Set<MetaData> metaDataList) {
        this.url = url;
        this.metaDataList = metaDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlStats urlStats = (UrlStats) o;
        return id == urlStats.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
