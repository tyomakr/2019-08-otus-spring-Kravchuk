package ru.otus.spring.library.jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="genreid", unique = true, nullable = false)
    private long genreId;
    @Column(name ="genrename")
    private String genreName;


    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
