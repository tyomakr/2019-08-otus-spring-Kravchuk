package ru.otus.spring.library.webflux.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.library.webflux.domain.Genre;

@Data
@AllArgsConstructor
public class GenreDto {

    private String id;
    private String genreTitle;

}
