package ru.otus.spring.library.webmvc.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.library.webmvc.domain.Genre;

@Data
@AllArgsConstructor
public class GenreDto {

    private String id;
    private String genreTitle;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getGenreTitle());
    }
}
