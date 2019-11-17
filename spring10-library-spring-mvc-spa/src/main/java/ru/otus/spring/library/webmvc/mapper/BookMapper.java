package ru.otus.spring.library.webmvc.mapper;

import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.rest.dto.BookDto;

import java.util.List;

public interface BookMapper {

    Book dtoToBook (BookDto bookDto);

    BookDto bookDtoToDomain(Book book);

    List<BookDto> domainToDto(List<Book> book);
}
