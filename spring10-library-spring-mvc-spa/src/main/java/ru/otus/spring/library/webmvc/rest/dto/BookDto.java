package ru.otus.spring.library.webmvc.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private String authors;
    private String genres;
}
