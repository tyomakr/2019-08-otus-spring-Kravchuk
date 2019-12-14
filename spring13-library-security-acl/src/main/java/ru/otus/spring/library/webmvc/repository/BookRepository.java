package ru.otus.spring.library.webmvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.spring.library.webmvc.domain.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByTitle(String title);

    @Override
    @PreAuthorize(value = "hasPermission(#book,'DELETE')")
    void delete(Book book);
}
