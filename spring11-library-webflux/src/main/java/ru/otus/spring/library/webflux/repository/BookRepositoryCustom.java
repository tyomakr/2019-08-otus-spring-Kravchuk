package ru.otus.spring.library.webflux.repository;

import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.domain.Book;

public interface BookRepositoryCustom {

    Mono<Book> saveBook(Book book);
}
