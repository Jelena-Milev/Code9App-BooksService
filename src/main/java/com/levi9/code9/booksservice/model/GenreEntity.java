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
@Entity(name = "Genre")
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "genre",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<BookGenre> books = new ArrayList<>();
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "genres")
//    private Set<BookEntity> books;

//    @JoinTable(name="Genre_Books", joinColumns = {@JoinColumn(name="genre_id", referencedColumnName = "id")},
//    inverseJoinColumns = {@JoinColumn(name="book_id", referencedColumnName = "id")})


    public GenreEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof GenreEntity)) return false;
        GenreEntity that = (GenreEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
