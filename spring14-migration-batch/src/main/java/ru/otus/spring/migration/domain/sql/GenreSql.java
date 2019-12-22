package ru.otus.spring.migration.domain.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="genreid", unique = true, nullable = false)
    private long genreId;
    @Column(name ="genrename")
    private String genreName;


    public GenreSql(String genreName) {
        this.genreName = genreName;
    }
}
