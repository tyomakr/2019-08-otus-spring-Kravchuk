package ru.otus.spring.library.webflux.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {

    private String id;
    private String authorName;


}
