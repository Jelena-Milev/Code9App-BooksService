package com.levi9.code9.booksservice.model;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "Author")
@Table(name="author")
public class AuthorEntity implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "author_id_seq", initialValue = 1)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AuthorEntity)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                dateOfBirth.equals(that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
