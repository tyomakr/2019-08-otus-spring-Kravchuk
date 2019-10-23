package ru.otus.spring.data.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.jpa.domain.Book;
import ru.otus.spring.data.jpa.domain.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByBook(Book book);
}
