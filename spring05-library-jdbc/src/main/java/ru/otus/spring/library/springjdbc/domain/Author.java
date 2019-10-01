package ru.otus.spring.library.springjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {

    private long id;
    private String authorName;


    public Author(String authorName) {
        this.authorName = authorName;
    }
}
