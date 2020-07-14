package com.levi9.code9.booksservice.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Genre")
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @EqualsAndHashCode.Include
    private String name;

    public GenreEntity(String name) {
        this.name = name;
    }

    @OneToMany(
            mappedBy = "genre",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<BookGenre> books;
}
