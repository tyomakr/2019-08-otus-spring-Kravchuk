package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.model.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByTitle(String title);
}