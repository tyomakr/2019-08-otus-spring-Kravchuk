package ru.otus.spring.library.webmvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.webmvc.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom {

    List<Comment> findAllByBook_Id(String bookId);
}