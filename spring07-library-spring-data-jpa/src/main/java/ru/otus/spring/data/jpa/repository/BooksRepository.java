package ru.otus.spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.jpa.domain.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
