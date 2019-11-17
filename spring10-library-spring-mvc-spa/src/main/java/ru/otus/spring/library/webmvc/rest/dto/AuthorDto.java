package ru.otus.spring.library.webmvc.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.library.webmvc.domain.Author;

@Data
@AllArgsConstructor
public class AuthorDto {

    private String id;
    private String authorName;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getAuthorName());
    }
}
