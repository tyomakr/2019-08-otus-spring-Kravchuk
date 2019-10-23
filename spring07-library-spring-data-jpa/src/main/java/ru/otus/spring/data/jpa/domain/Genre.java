package ru.otus.spring.data.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
