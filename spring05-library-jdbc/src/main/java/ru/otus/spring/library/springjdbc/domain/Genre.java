package ru.otus.spring.library.springjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {

    private int id;
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
