package ru.otus.spring.library.webmvc.mapper;

import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.rest.dto.BookDto;

import java.util.List;

public interface BookMapper {

    List<BookDto> domainToDto(List<Book> book);
}
